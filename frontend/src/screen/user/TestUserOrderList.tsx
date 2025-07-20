import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import OrderDetailPopup from "../../components/OrderDetailPopup";

export default function UserOrderList() {
  const navigate = useNavigate();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const email = queryParams.get("username");

  const [orders, setOrders] = useState<any[]>([]);
  const [selectedOrder, setSelectedOrder] = useState<any | null>(null);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);
  const [statusFilter, setStatusFilter] = useState("");
  const [dateFilter, setDateFilter] = useState("");

  useEffect(() => {
    if (!email) return;

    axios
      .get("http://localhost:8080/orders/user/findorder", {
        params: { arg0: email, email },
      })
      .then((res) => {
        setOrders(res.data.data);
      })
      .catch((err) => {
        alert(err.response?.data?.msg || "주문 조회 중 오류 발생");
        navigate("/");
      });
  }, [email]);

  const toggleSelect = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  const filteredOrders = orders.filter((order) => {
    const matchesStatus = statusFilter
      ? statusFilter === (order.deliveryStatus ? "Delivered" : "On the way")
      : true;
    const matchesDate = dateFilter
      ? order.createdAt.slice(0, 10) === dateFilter
      : true;
    return matchesStatus && matchesDate;
  });

  const handleRowClick = (order: any) => setSelectedOrder(order);
  const closePopup = () => setSelectedOrder(null);

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
        <span style={{ color: "#ffffff", fontSize: "2.5rem", fontWeight: "bold" }}>
          Grids & Circle
        </span>
      </div>

      {/* Content */}
      <div
        style={{
          backgroundColor: "#f8f8f8",
          width: "100%",
          maxWidth: "1044px",
          borderRadius: "1rem",
          padding: "2rem",
        }}
      >
        {/* Filters */}
        <div style={{ display: "flex", gap: "2.5rem", marginBottom: "1.5rem" }}>
          <div>
            <label style={{ color: "#b7b7b7", fontWeight: 600 }}>Delivery ▾</label>
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

          <div>
            <label style={{ color: "#b7b7b7", fontWeight: 600 }}>Order Date ▾</label>
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

        {/* Table Rows */}
        {filteredOrders.map((order) => {
          const date = order.createdAt.slice(0, 10);
          const time = order.createdAt.slice(11, 19);
          const status = order.deliveryStatus ? "Delivered" : "On the way";

          return (
            <div
              key={order.orderId}
              style={{
                display: "grid",
                gridTemplateColumns: "2fr 1fr 1.5fr 1.5fr 1fr",
                padding: "1rem 0",
                borderBottom: "1px solid #e0e0e0",
                color: "#b7b7b7",
                fontWeight: 600,
                alignItems: "center",
              }}
            >
              <div onClick={() => handleRowClick(order)} style={{ cursor: "pointer" }}>
                {order.email}
              </div>
              <div onClick={() => handleRowClick(order)} style={{ cursor: "pointer" }}>
                {order.orderId}
              </div>
              <div onClick={() => handleRowClick(order)} style={{ cursor: "pointer" }}>
                {status}
              </div>
              <div onClick={() => handleRowClick(order)} style={{ cursor: "pointer" }}>
                <div>{date.replaceAll("-", ".").slice(2)}</div>
                <div>{time}</div>
              </div>
              <div>
                <input
                  type="checkbox"
                  checked={selectedIds.includes(order.orderId)}
                  onChange={() => toggleSelect(order.orderId)}
                  style={{ width: "1rem", height: "1rem" }}
                />
              </div>
            </div>
          );
        })}

        {/* 선택된 ID 표시 */}
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
      </div>

      {/* 상세 팝업 */}
      {selectedOrder && (
        <OrderDetailPopup
          orderId={selectedOrder.orderId}
          onClose={closePopup}
          products={selectedOrder.orderItems.map((item: any) => ({
            id: item.productId,
            name: item.productName,
            quantity: item.orderCount,
            price: 0,
            image: "https://via.placeholder.com/80",
          }))}
        />
      )}
    </div>
  );
}
