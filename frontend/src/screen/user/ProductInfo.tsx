import { useState } from 'react';
import { useNavigate } from 'react-router-dom'; 
import OrderList from '../../components/User/OrderList';
import Summary from '../../components/User/Summary';
import products from '../../resources/products';


export default function ProductInfo() {
  const navigate = useNavigate(); 


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
        onClick={() => navigate('/')} 
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
