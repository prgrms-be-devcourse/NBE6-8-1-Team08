import { useState } from "react";
import axios from "axios";

interface Product {
  id: number;
  name: string;
  price: number;
  quantity: number;
  image: string;
}

interface OrderDetailPopupProps {
  onClose: () => void;
  orderId: number;
  products: Product[];
  email: string;
}

export default function OrderDetailPopup({
  onClose,
  orderId,
  products,
  email,
}: OrderDetailPopupProps) {
  const [checkedItems, setCheckedItems] = useState<number[]>([]);

  const toggleCheckbox = (id: number) => {
    setCheckedItems((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    );
  };

  const handleCancel = async () => {
    const selectedProducts = products
      .filter((product) => checkedItems.includes(product.id))
      .map((product) => ({ productName: product.name }));

    if (selectedProducts.length === 0) {
      return alert("취소할 상품을 선택하세요.");
    }

    try {
      const response = await axios.post("/orders/cancel-detail", {
        email,
        products: selectedProducts,
      });

      if (response.data.msg === "Cancel orderItem successful") {
        alert("취소되었습니다.");
        window.location.reload();
      } else {
        alert("취소 실패: " + response.data.msg);
      }
    } catch (error) {
      alert("서버 오류 발생");
      console.error(error);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex justify-center items-center z-50">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-2xl max-h-[90vh] overflow-y-auto relative p-6">
        {/* 상단 */}
        <div className="flex justify-between items-center border-b pb-4 mb-4">
          <h2 className="text-xl font-bold">Order ID: {orderId}</h2>
          <button onClick={onClose} className="text-2xl text-gray-600 hover:text-gray-800 font-bold">
            ×
          </button>
        </div>

        {/* 상품 리스트 */}
        <ul className="divide-y divide-gray-200">
          {products.map((product) => (
            <li key={product.id} className="flex items-center py-4 gap-4">
              <input
                type="checkbox"
                checked={checkedItems.includes(product.id)}
                onChange={() => toggleCheckbox(product.id)}
                className="w-5 h-5"
              />
              <img
                src={product.image}
                alt={product.name}
                className="w-20 h-20 object-cover rounded border"
              />
              <div className="flex flex-col">
                <span className="font-semibold">{product.name}</span>
                <span className="text-sm text-gray-600">가격: ₩{product.price}</span>
                <span className="text-sm text-gray-600">수량: {product.quantity}</span>
              </div>
            </li>
          ))}
        </ul>

        {/* 하단 버튼 */}
        <div className="flex justify-end mt-6">
          <button
            onClick={handleCancel}
            className="bg-black text-white py-2 px-6 rounded-lg font-semibold hover:bg-gray-800"
          >
            취소하기
          </button>
        </div>
      </div>
    </div>
  );
}
