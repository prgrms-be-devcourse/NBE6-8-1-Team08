import { useState, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import OrderDetailPopup from "../../components/OrderDetailPopup";
import products from "../../resources/products";

interface OrderItem {
  orderItemId: number;
  productName: string;
  orderCount: number;
  orderItemStatus: boolean;
  price?: number;
  image?: string;
}

interface Order {
  orderId: number;
  email: string;
  address: string;
  createdAt: string;
  orderStatus: boolean;
  deliveryStatus: boolean;
  orderItems: OrderItem[];
  isCanceled?: boolean; // 취소 여부 추가
}

export default function UserOrderList() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const [orders, setOrders] = useState<Order[]>([]);
  const [filteredOrders, setFilteredOrders] = useState<Order[]>([]);

  const [statusFilter, setStatusFilter] = useState("");
  const [dateFilter, setDateFilter] = useState("");

  const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);

  const [isBatchCancelling, setIsBatchCancelling] = useState(false);

  const email = searchParams.get("username") || "";

  // 서버에서 이메일 기준 주문 조회
  useEffect(() => {
    if (!email) {
      alert("이메일 정보가 없습니다.");
      navigate("/user/searchorder");
      return;
    }

    fetch(`http://localhost:8080/orders/user/findorder?email=${encodeURIComponent(email)}`)
      .then((res) => {
        if (!res.ok) throw new Error("조회 실패");
        return res.json();
      })
      .then((data) => {
        // API 응답에서 isCanceled 초기값 false로 세팅
        const loadedOrders: Order[] = (data.data || []).map((order: Order) => ({
          ...order,
          isCanceled: false,
        }));
        setOrders(loadedOrders);
      })
      .catch((err) => {
        alert("주문 조회 중 오류가 발생했습니다.");
        console.error(err);
      });
  }, [email, navigate]);

  // 필터 적용
  useEffect(() => {
    let filtered = [...orders];

    if (statusFilter) {
      const filterBool = statusFilter === "Delivered";
      filtered = filtered.filter((o) => o.deliveryStatus === filterBool);
    }

    if (dateFilter) {
      filtered = filtered.filter(
        (o) => o.createdAt.slice(0, 10) === dateFilter
      );
    }

    setFilteredOrders(filtered);
  }, [orders, statusFilter, dateFilter]);

  // 체크박스 토글 (취소된 주문은 선택 못 하게)
  const toggleSelect = (orderId: number) => {
    const order = orders.find((o) => o.orderId === orderId);
    if (order?.isCanceled) return; // 취소된 주문 선택 불가

    setSelectedIds((prev) =>
      prev.includes(orderId)
        ? prev.filter((id) => id !== orderId)
        : [...prev, orderId]
    );
  };

  // 주문 행 클릭 시 팝업 열기 (취소된 주문은 팝업 안 띄움)
  const handleRowClick = (order: Order) => {
    if (order.isCanceled) return;

    const enrichedItems = order.orderItems.map((item) => {
      const matchedProduct = products.find(
        (p) => p.engName === item.productName
      );
      return {
        ...item,
        price: matchedProduct ? Number(matchedProduct.price) : 0,
        image: matchedProduct ? matchedProduct.imgUrl : "",
      };
    });

    setSelectedOrder({ ...order, orderItems: enrichedItems });
  };

  // 팝업 닫기
  const closePopup = () => {
    setSelectedOrder(null);
  };

  // 팝업에서 상품 취소 후 처리 함수 (팝업 컴포넌트에서 호출)
  const handlePopupCancel = async (canceledProductNames: string[]) => {
    if (!selectedOrder) return;

    const body = {
      email: selectedOrder.email,
      products: canceledProductNames.map((name) => ({ productName: name })),
    };

    try {
      const res = await fetch("http://localhost:8080/orders/cancel-detail", {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });
      if (!res.ok) throw new Error("취소 실패");

      // 주문내 상품 상태 업데이트
      setOrders((prevOrders) =>
        prevOrders.map((order) => {
          if (order.orderId === selectedOrder.orderId) {
            const updatedItems = order.orderItems.map((item) =>
              canceledProductNames.includes(item.productName)
                ? { ...item, orderItemStatus: false }
                : item
            );
            // 모든 상품이 취소되었으면 isCanceled = true
            const allCanceled = updatedItems.every((item) => item.orderItemStatus === false);
            return { ...order, orderItems: updatedItems, isCanceled: allCanceled };
          }
          return order;
        })
      );

      setSelectedOrder(null);
    } catch (e) {
      alert("상품 취소 중 오류가 발생했습니다.");
      console.error(e);
    }
  };

  // 리스트에서 일괄 취소 처리
  const handleBatchCancel = async () => {
    if (selectedIds.length === 0) {
      alert("취소할 주문을 선택해주세요.");
      return;
    }

    setIsBatchCancelling(true);

    try {
      for (const orderId of selectedIds) {
        const order = orders.find((o) => o.orderId === orderId);
        if (!order) continue;

        // 이미 취소된 주문은 패스
        if (order.isCanceled) continue;

        // 취소 안된 상품만 골라서 보내기
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

      // 취소 성공시 로컬 상태 반영 (모든 선택 주문 isCanceled = true)
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
          position: "relative",
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
              <option value="Delivered">Delivered</option>
              <option value="On the way">On the way</option>
            </select>
          </div>

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
          <div>Username</div>
          <div>Order ID</div>
          <div>Delivery Status</div>
          <div>Order Date</div>
          <div>Cancel</div>
        </div>

        {/* Order Rows */}
        {filteredOrders.map((order) => {
          const isCanceled = order.isCanceled ?? false;

          return (
            <div
              key={order.orderId}
              style={{
                display: "grid",
                gridTemplateColumns: "2fr 1fr 1.5fr 1.5fr 1fr",
                padding: "1rem 0",
                borderBottom: "1px solid #e0e0e0",
                color: isCanceled ? "#a0a0a0" : "#474747",
                fontWeight: 600,
                alignItems: "center",
                cursor: isCanceled ? "default" : "pointer",
                opacity: isCanceled ? 0.5 : 1,
              }}
              onClick={() => {
                if (!isCanceled) handleRowClick(order);
              }}
            >
              <div>{order.email}</div>
              <div>{order.orderId}</div>
              <div>{order.deliveryStatus ? "Delivered" : "On the way"}</div>
              <div>
                {order.createdAt.slice(0, 10).replaceAll("-", ".")}
                <br />
                {order.createdAt.slice(11, 19)}
              </div>
              <div
                style={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "flex-start", // 왼쪽 정렬로 변경
                  fontWeight: "bold",
                  userSelect: "none",
                  paddingLeft: "0.5rem", // 필요시 약간 왼쪽 여백 추가
                }}
                onClick={(e) => e.stopPropagation()}
              >
                {isCanceled ? "O" : (
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

        {/* 선택된 주문 정보 */}
        {selectedIds.length > 0 && (
          <div
            style={{
              marginTop: "1rem",
              padding: "1rem",
              backgroundColor: "#ffffff",
              borderRadius: "0.5rem",
              color: "#474747",
              fontWeight: 600,
            }}
          >
            ✅ 선택된 주문 ID: {selectedIds.join(", ")}
          </div>
        )}

        {/* 일괄 취소 버튼 */}
        <button
          onClick={handleBatchCancel}
          disabled={isBatchCancelling}
          style={{
            marginTop: "2rem",
            marginLeft: "55rem",
            width: "160px",
            padding: "0.7rem 1rem",
            backgroundColor: isBatchCancelling ? "#555" : "#000",
            color: "#fff",
            fontWeight: 600,
            borderRadius: "0.5rem",
            border: "none",
            cursor: isBatchCancelling ? "not-allowed" : "pointer",
            alignSelf: "flex-start",
          }}
        >
          {isBatchCancelling ? "취소 중..." : "취소하기"}
        </button>
      </div>

      {/* 상세 주문 팝업 */}
      {selectedOrder && (
        <OrderDetailPopup
          orderId={selectedOrder.orderId}
          email={selectedOrder.email}
          onClose={closePopup}
          onCancel={handlePopupCancel} // 팝업에서 취소 후 호출되는 함수
          products={selectedOrder.orderItems.map((item) => ({
            id: item.orderItemId,
            name: item.productName,
            price: item.price || 0,
            quantity: item.orderCount,
            image: item.image || "",
            orderItemStatus: item.orderItemStatus,
          }))}
        />
      )}
    </div>
  );
}
