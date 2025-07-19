import { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // 추가
import OrderList from '../../components/OrderList';
import Summary from '../../components/Summary';

export default function ProductInfo() {
  const navigate = useNavigate(); // 추가

  const products = [
    { korName: '커피콩', engName: 'Columbia Bean', price: '50000', imgUrl: 'https://via.placeholder.com/80' },
    { korName: '커피콩', engName: 'Ethiopia Yirgacheffe', price: '45000', imgUrl: 'https://via.placeholder.com/80' },
    { korName: '커피콩', engName: 'Guatemala Antigua', price: '52000', imgUrl: 'https://via.placeholder.com/80' },
  ];

  const [cart, setCart] = useState({});

  const addToCart = (product) => {
    setCart((prev) => {
      const currentCount = prev[product.engName] || 0;
      return { ...prev, [product.engName]: currentCount + 1 };
    });
  };

  const decreaseCount = (engName) => {
    setCart((prev) => {
      const currentCount = prev[engName] || 0;
      if (currentCount <= 1) {
        const { [engName]: _, ...rest } = prev;
        return rest;
      } else {
        return { ...prev, [engName]: currentCount - 1 };
      }
    });
  };

  return (
    <div style={{ minHeight: '100vh', minWidth: '1500px', backgroundColor: '#DCDCDC', display: 'flex', flexDirection: 'column', padding: '2rem' }}>
      <div
        style={{ height: '15vh', display: 'flex', alignItems: 'center', justifyContent: 'center', marginBottom: '2rem', cursor: 'pointer' }}
        onClick={() => navigate('/')} // 클릭 시 홈으로 이동
      >
        <div style={{ fontSize: '3rem', fontWeight: 'bold', color: '#484848' }}>
          Grids & Circle
        </div>
      </div>

      <div style={{ display: 'flex', flex: 1, gap: '2rem' }}>
        <OrderList products={products} onAdd={addToCart} />
        <Summary cart={cart} products={products} onDecrease={decreaseCount} />
      </div>
    </div>
  );
}
