import { useState } from "react";

interface Product {
  id: number;
  name: string;
  price: number;
  quantity: number;
  image: string;
}

interface OrderDetailPopupProps {
  orderId: number;
  email: string;
  onClose: () => void;
  products: Product[];
}

export default function OrderDetailPopup({
  orderId,
  email,
  onClose,
  products,
}: OrderDetailPopupProps) {
  const [checkedIds, setCheckedIds] = useState<number[]>([]);
  const [loading, setLoading] = useState(false);

  const toggleCheck = (id: number) => {
    setCheckedIds((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  const handleCancel = async () => {
    if (checkedIds.length === 0) {
      alert("취소할 상품을 선택해주세요.");
      return;
    }

    const selectedProducts = products.filter((p) => checkedIds.includes(p.id));

    const body = {
      email,
      products: selectedProducts.map((p) => ({
        productName: p.name,
      })),
    };

    setLoading(true);
    try {
      const res = await fetch("http://localhost:8080/orders/cancel-detail", {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });

      if (!res.ok) throw new Error("취소 요청 실패");

      const data = await res.json();
      alert(data.msg || "취소 요청 성공");
      onClose();
      window.location.reload();
    } catch (error) {
      alert("취소 요청 중 오류가 발생했습니다.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      style={{
        position: "fixed",
        top: 0,
        left: 0,
        width: "100vw",
        height: "100vh",
        backgroundColor: "rgba(0,0,0,0.5)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 999,
      }}
      onClick={onClose}
    >
      <div
        onClick={(e) => e.stopPropagation()}
        style={{
          backgroundColor: "#fff",
          borderRadius: "1rem",
          width: "600px",
          maxHeight: "80vh",
          overflowY: "auto",
          padding: "1.5rem",
          display: "flex",
          flexDirection: "column",
        }}
      >
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            marginBottom: "1rem",
            alignItems: "center",
          }}
        >
          <h2>Order ID: {orderId}</h2>
          <button
            onClick={onClose}
            style={{
              fontSize: "1.5rem",
              background: "none",
              border: "none",
              cursor: "pointer",
            }}
            aria-label="닫기"
          >
            &times;
          </button>
        </div>

        <div
          style={{
            display: "flex",
            flexDirection: "column",
            gap: "1rem",
          }}
        >
          {products.map(({ id, name, price, quantity, image }) => (
            <div
              key={id}
              style={{
                display: "flex",
                alignItems: "center",
                gap: "1rem",
                borderBottom: "1px solid #ccc",
                paddingBottom: "0.5rem",
              }}
            >
              <img
                src={image}
                alt={name}
                style={{ width: "80px", height: "80px", objectFit: "cover" }}
              />
              <div style={{ flexGrow: 1 }}>
                <div style={{ fontWeight: 600 }}>{name}</div>
                <div>가격: {price.toLocaleString()} 원</div>
                <div>수량: {quantity}</div>
              </div>
              <input
                type="checkbox"
                checked={checkedIds.includes(id)}
                onChange={() => toggleCheck(id)}
                style={{ width: "1.2rem", height: "1.2rem" }}
                disabled={loading}
              />
            </div>
          ))}
        </div>

        <button
          onClick={handleCancel}
          disabled={loading}
          style={{
            marginTop: "1rem",
            alignSelf: "flex-end",
            padding: "0.7rem 1.5rem",
            backgroundColor: "#000",
            color: "#fff",
            border: "none",
            borderRadius: "0.5rem",
            fontWeight: "600",
            cursor: loading ? "not-allowed" : "pointer",
          }}
        >
          {loading ? "취소 중..." : "취소"}
        </button>
      </div>
    </div>
  );
}
