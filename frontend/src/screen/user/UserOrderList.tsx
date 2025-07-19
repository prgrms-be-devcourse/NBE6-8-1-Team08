import { useState } from "react";
import { useNavigate } from "react-router-dom";
import OrderDetailPopup from "../../components/OrderDetailPopup";

export default function UserOrderList() {
  const navigate = useNavigate();
  const allOrders = [
    {
      id: 1311,
      username: "mario@gmail.com",
      status: "On the way",
      date: "2025-03-11",
      time: "17:42:21",
    },
    {
      id: 1312,
      username: "luigi@gmail.com",
      status: "Delivered",
      date: "2025-03-10",
      time: "15:20:05",
    },
    {
      id: 1313,
      username: "luigi@gmail.com",
      status: "Delivered",
      date: "2025-03-10",
      time: "15:20:05",
    },
    {
      id: 1314,
      username: "peach@gmail.com",
      status: "On the way",
      date: "2025-03-09",
      time: "14:10:55",
    },
  ];

  const [selectedOrder, setSelectedOrder] = useState<null | typeof allOrders[0]>(null);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);
  const [statusFilter, setStatusFilter] = useState("");
  const [dateFilter, setDateFilter] = useState("");

  const toggleSelect = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  const filteredOrders = allOrders.filter((order) => {
    const matchesStatus = statusFilter ? order.status === statusFilter : true;
    const matchesDate = dateFilter ? order.date === dateFilter : true;
    return matchesStatus && matchesDate;
  });

  const handleRowClick = (order: typeof allOrders[0]) => {
    setSelectedOrder(order);
  };

  const closePopup = () => {
    setSelectedOrder(null);
  };

  const handleCancelClick = () => {
    alert("취소되었습니다.");
    window.location.reload();
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
              <option value="On the way">On the way</option>
              <option value="Delivered">Delivered</option>
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
        {filteredOrders.map((order) => (
          <div
            key={`${order.id}-${order.time}`}
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
              {order.username}
            </div>
            <div onClick={() => handleRowClick(order)} style={{ cursor: "pointer" }}>
              {order.id}
            </div>
            <div onClick={() => handleRowClick(order)} style={{ cursor: "pointer" }}>
              {order.status}
            </div>
            <div onClick={() => handleRowClick(order)} style={{ cursor: "pointer" }}>
              <div>{order.date.replaceAll("-", ".").slice(2)}</div>
              <div>{order.time}</div>
            </div>
            <div style={{ display: "flex", alignItems: "center", gap: "0.5rem" }}>
              <span>O</span>
              <input
                type="checkbox"
                checked={selectedIds.includes(order.id)}
                onChange={() => toggleSelect(order.id)}
                style={{ width: "1rem", height: "1rem" }}
              />
            </div>
          </div>
        ))}

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

        {/* 취소 버튼 */}
        <button
          onClick={handleCancelClick}
          style={{
            marginTop: "2rem",
            marginLeft: "55rem",
            width: "160px",
            padding: "0.7rem 1rem",
            backgroundColor: "#000000",
            color: "#ffffff",
            fontWeight: 600,
            borderRadius: "0.5rem",
            border: "none",
            cursor: "pointer",
            alignSelf: "flex-start",
          }}
        >
          취소하기
        </button>
      </div>

      {/* 팝업 컴포넌트 */}
      {selectedOrder && (
        <OrderDetailPopup
          orderId={selectedOrder.id}
          onClose={closePopup}
          products={[
            {
              id: 1,
              name: "Columbia Bean",
              price: 50000,
              quantity: 1,
              image: "https://via.placeholder.com/80",
            },
            {
              id: 2,
              name: "Columbia Bean",
              price: 50200,
              quantity: 1,
              image: "https://via.placeholder.com/80",
            },
          ]}
        />
      )}
    </div>
  );
}
