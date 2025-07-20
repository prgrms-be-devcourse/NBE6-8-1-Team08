import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import OrderList from '../../components/User/OrderList';
import Summary from '../../components/User/Summary';
import products from '../../../public/resources/products'; 

export default function ProductInfo() {
  const navigate = useNavigate();
  const [cart, setCart] = useState({});

  const addToCart = (product) => {
    setCart((prev) => {
      const currentItem = prev[product.engName];
      const currentCount = currentItem ? currentItem.count : 0;
      return {
        ...prev,
        [product.engName]: {
          PRODUCT_ID: product.PRODUCT_ID,
          price: product.price,
          count: currentCount + 1,
        },
      };
    });
  };

  const decreaseCount = (engName) => {
    setCart((prev) => {
      const currentItem = prev[engName];
      if (!currentItem) return prev;
      if (currentItem.count <= 1) {
        const { [engName]: _, ...rest } = prev;
        return rest;
      } else {
        return {
          ...prev,
          [engName]: {
            ...currentItem,
            count: currentItem.count - 1,
          },
        };
      }
    });
  };

  // 여기서 products가 제대로 전달되는지 확인
  console.log('ProductInfo에서 전달되는 products:', products);

  return (
    <div style={{ minHeight: '100vh', minWidth: '1500px', backgroundColor: '#DCDCDC', display: 'flex', flexDirection: 'column', padding: '2rem' }}>
      <div style={{ height: '15vh', display: 'flex', alignItems: 'center', justifyContent: 'center', marginBottom: '2rem', cursor: 'pointer' }} onClick={() => navigate('/')}>
        <div style={{ fontSize: '3rem', fontWeight: 'bold', color: '#484848' }}>
          Grids & Circle
        </div>
      </div>

      <div style={{ display: 'flex', flex: 1, gap: '2rem' }}>
        {/* OrderList에 products와 onAdd 전달 */}
        <OrderList products={products} onAdd={addToCart} />
        <Summary cart={cart} products={products} onDecrease={decreaseCount} />
      </div>
    </div>
  );
}
