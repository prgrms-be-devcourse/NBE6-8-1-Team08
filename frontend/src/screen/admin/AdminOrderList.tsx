import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import OrderDetailPopup from "../../components/OrderDetailPopup";

interface OrderItem {
  orderItemId: number;
  productName: string;
  orderCount: number;
  productPrice: number;
  orderItemStatus: boolean;
}

interface Order {
  orderId: number;
  email: string;
  address?: string;
  createdAt: string;
  orderStatus?: boolean;
  deliveryStatus: boolean;
  orderItems: OrderItem[];
  isCanceled?: boolean;
}

export default function AdminOrderList() {
  const navigate = useNavigate();

  const [orders, setOrders] = useState<Order[]>([]);
  const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);
  const [isBatchCancelling, setIsBatchCancelling] = useState(false);

  // 필터 상태들
  const [usernameQuery, setUsernameQuery] = useState("");
  const [statusFilter, setStatusFilter] = useState("");
  const [dateFilter, setDateFilter] = useState("");

  // 주문 리스트 API 호출
  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const res = await axios.get("http://localhost:8080/admin/orders", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        });
        setOrders(res.data.data);
      } catch (error) {
        console.error("주문 목록 조회 실패", error);
        alert("주문 목록을 불러오는 중 오류가 발생했습니다.");
      }
    };

    fetchOrders();
  }, []);

  // 필터 적용된 주문 목록
  const filteredOrders = orders.filter((order) => {
    const matchesUsername = order.email
      .toLowerCase()
      .includes(usernameQuery.toLowerCase());

    const matchesStatus = statusFilter
      ? (order.deliveryStatus ? "On the way" : "Delivered") === statusFilter
      : true;

    const matchesDate = dateFilter
      ? order.createdAt.slice(0, 10) === dateFilter
      : true;

    return matchesUsername && matchesStatus && matchesDate;
  });

  // 선택 토글
  const toggleSelect = (orderId: number) => {
    const order = orders.find((o) => o.orderId === orderId);
    if (order?.isCanceled) return; // 취소된 주문 선택 불가

    setSelectedIds((prev) =>
      prev.includes(orderId)
        ? prev.filter((id) => id !== orderId)
        : [...prev, orderId]
    );
  };

  // 일괄 취소
  const handleBatchCancel = async () => {
    if (selectedIds.length === 0) {
      alert("취소할 주문을 선택해주세요.");
      return;
    }

    setIsBatchCancelling(true);

    try {
      for (const orderId of selectedIds) {
        const order = orders.find((o) => o.orderId === orderId);
        if (!order || order.isCanceled) continue;

        const activeProducts = order.orderItems
          .filter((item) => item.orderItemStatus !== false)
          .map((item) => ({ productName: item.productName }));

        if (activeProducts.length === 0) continue;

        const body = {
          email: order.email,
          products: activeProducts,
        };

        const res = await fetch("http://localhost:8080/orders/cancel-detail", {
          method: "PATCH",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(body),
        });

        if (!res.ok) throw new Error(`주문 ${orderId} 취소 실패`);
      }

      // 취소 상태 로컬 업데이트
      setOrders((prevOrders) =>
        prevOrders.map((order) =>
          selectedIds.includes(order.orderId)
            ? {
                ...order,
                orderItems: order.orderItems.map((item) => ({
                  ...item,
                  orderItemStatus: false,
                })),
                isCanceled: true,
              }
            : order
        )
      );
      setSelectedIds([]);
      alert("선택한 주문들 일괄 취소 완료");
    } catch (error) {
      alert("일괄 취소 처리 중 오류가 발생했습니다.");
      console.error(error);
    } finally {
      setIsBatchCancelling(false);
    }
  };

  const handleRowClick = (order: Order) => {
    if (order.isCanceled) return;
    setSelectedOrder(order);
  };

  const closePopup = () => {
    setSelectedOrder(null);
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        backgroundColor: "#474747",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        padding: "2rem 1rem",
      }}
    >
      {/* Logo */}
      <div
        onClick={() => navigate("/")}
        style={{
          display: "flex",
          alignItems: "center",
          marginBottom: "2rem",
          marginLeft: "-30%",
          cursor: "pointer",
        }}
      >
        <img
          src="https://i.postimg.cc/XYnmtRLS/logo.png"
          alt="Logo"
          style={{ width: "40px", height: "40px", marginRight: "0.5rem" }}
        />
        <span
          style={{
            color: "#ffffff",
            fontSize: "2.5rem",
            fontWeight: "bold",
          }}
        >
          Grids & Circle
        </span>
      </div>

      {/* Content Box */}
      <div
        style={{
          backgroundColor: "#f8f8f8",
          width: "100%",
          maxWidth: "1044px",
          borderRadius: "1rem",
          padding: "2rem",
          flexGrow: 1,
          display: "flex",
          flexDirection: "column",
          position: "relative", // 절대위치 버튼 기준
        }}
      >
        {/* Filters */}
        <div
          style={{
            display: "flex",
            gap: "2.5rem",
            marginBottom: "1.5rem",
            alignItems: "center",
          }}
        >
          {/* Username 검색 */}
          <div style={{ width: "400px" }}>
            <label
              style={{
                color: "#b7b7b7",
                fontWeight: 600,
                marginBottom: "0.3rem",
                display: "block",
              }}
            >
              Username
            </label>
            <input
              type="text"
              value={usernameQuery}
              onChange={(e) => setUsernameQuery(e.target.value)}
              placeholder="Search by email"
              style={{
                padding: "0.5rem",
                width: "100%",
                borderRadius: "0.5rem",
                border: "1px solid #ccc",
                fontWeight: 600,
              }}
            />
          </div>

          {/* Delivery Status 필터 */}
          <div>
            <label
              style={{
                color: "#b7b7b7",
                fontWeight: 600,
                marginBottom: "0.3rem",
                display: "block",
              }}
            >
              Delivery ▾
            </label>
            <select
              value={statusFilter}
              onChange={(e) => setStatusFilter(e.target.value)}
              style={{
                width: "8rem",
                padding: "0.5rem",
                borderRadius: "0.5rem",
                border: "1px solid #ccc",
                fontWeight: 600,
              }}
            >
              <option value="">All</option>
              <option value="On the way">On the way</option>
              <option value="Delivered">Delivered</option>
            </select>
          </div>

          {/* Order Date 필터 */}
          <div>
            <label
              style={{
                color: "#b7b7b7",
                fontWeight: 600,
                marginBottom: "0.3rem",
                display: "block",
              }}
            >
              Order Date ▾
            </label>
            <input
              type="date"
              value={dateFilter}
              onChange={(e) => setDateFilter(e.target.value)}
              style={{
                width: "8rem",
                padding: "0.5rem",
                borderRadius: "0.5rem",
                border: "1px solid #ccc",
                fontWeight: 600,
              }}
            />
          </div>
        </div>

        {/* Table Header */}
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "2fr 1fr 1.5fr 1.5fr 1fr",
            padding: "0.5rem 0",
            borderBottom: "1px solid #d0d0d0",
            color: "#b7b7b7",
            fontWeight: 600,
          }}
        >
          <div>Email</div>
          <div>Order ID</div>
          <div>Delivery Status</div>
          <div>Order Date</div>
          <div>Cancel</div>
        </div>

        {/* 주문 리스트 */}
        {filteredOrders.map((order) => {
          const isCanceled = order.isCanceled ?? false;
          return (
            <div
              key={order.orderId}
              style={{
                opacity: isCanceled ? 0.5 : 1,
                color: isCanceled ? "#a0a0a0" : "#474747",
                fontWeight: 600,
                cursor: isCanceled ? "default" : "pointer",
                display: "grid",
                gridTemplateColumns: "2fr 1fr 1.5fr 1.5fr 1fr",
                padding: "1rem 0",
                borderBottom: "1px solid #e0e0e0",
                alignItems: "center",
              }}
              onClick={() => handleRowClick(order)}
            >
              <div>{order.email}</div>
              <div>{order.orderId}</div>
              <div>{order.deliveryStatus ? "On the way" : "Delivered"}</div>
              <div>
                {order.createdAt.slice(0, 10).replaceAll("-", ".")}
                <br />
                {order.createdAt.slice(11, 19)}
              </div>
              <div
                style={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "flex-start",
                  fontWeight: "bold",
                  userSelect: "none",
                  paddingLeft: "0.5rem",
                }}
                onClick={(e) => e.stopPropagation()}
              >
                {isCanceled ? (
                  "O"
                ) : (
                  <input
                    type="checkbox"
                    checked={selectedIds.includes(order.orderId)}
                    onChange={() => toggleSelect(order.orderId)}
                    style={{ width: "1rem", height: "1rem" }}
                  />
                )}
              </div>
            </div>
          );
        })}

        {/* 일괄 취소 버튼 (우측 하단 고정) */}
        <button
          onClick={handleBatchCancel}
          disabled={isBatchCancelling}
          style={{
            position: "absolute",
            bottom: "1.5rem",
            right: "1.5rem",
            width: "160px",
            padding: "0.7rem 1rem",
            backgroundColor: isBatchCancelling ? "#555" : "#000",
            color: "#fff",
            fontWeight: 600,
            borderRadius: "0.5rem",
            border: "none",
            cursor: isBatchCancelling ? "not-allowed" : "pointer",
          }}
        >
          {isBatchCancelling ? "취소 중..." : "일괄 취소"}
        </button>
      </div>

      {/* 상세 주문 팝업 */}
      {selectedOrder && (
        <OrderDetailPopup
          orderId={selectedOrder.orderId}
          email={selectedOrder.email}
          onClose={closePopup}
          products={selectedOrder.orderItems.map((item) => ({
            id: item.orderItemId,
            name: item.productName,
            price: item.productPrice,
            quantity: item.orderCount,
            image: "",
            orderItemStatus: item.orderItemStatus,
          }))}
        />
      )}
    </div>
  );
}
