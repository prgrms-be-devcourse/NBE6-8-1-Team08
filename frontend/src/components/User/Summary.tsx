import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Summary({ cart, products, onDecrease }) {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [address, setAddress] = useState('');
  const [zipcode, setZipcode] = useState('');
  const [errors, setErrors] = useState({
    email: '',
    address: '',
    zipcode: '',
  });

  const validateEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
  };

  const handleSubmit = async () => {
    const newErrors = { email: '', address: '', zipcode: '' };
    let hasError = false;
  
    if (!email) {
      newErrors.email = '필수 입력값입니다';
      hasError = true;
    } else if (!validateEmail(email)) {
      newErrors.email = '이메일 형식이 아닙니다';
      hasError = true;
    }
  
    if (!address) {
      newErrors.address = '필수 입력값입니다';
      hasError = true;
    }
  
    if (!zipcode) {
      newErrors.zipcode = '필수 입력값입니다';
      hasError = true;
    }
  
    setErrors(newErrors);
    if (hasError) return;
  
    const fullAddress = `${address} ${zipcode}`.trim();
  
    // productId는 products 배열 index + 1, count는 장바구니 갯수로 매핑
    const orderItems = Object.entries(cart).map(([engName, count]) => {
      const productIndex = products.findIndex((p) => p.engName === engName);
      return {
        productId: productIndex + 1,  // 1부터 시작
        count,
      };
    });
  
    const orderData = {
      email,
      address: fullAddress,
      orderItems,
    };
  
    try {
      const response = await fetch('http://localhost:8080/orders/user/order', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(orderData),
      });
  
      if (!response.ok) throw new Error('주문 실패');
  
      alert('결제가 완료되었습니다.');
      window.location.href = 'http://localhost:5137/';
    } catch (error) {
      alert('결제 요청 중 오류가 발생했습니다.');
      console.error(error);
    }
  };
  
  
  const renderLabel = (text, error) => (
    <div style={{ display: 'flex', alignItems: 'center', marginBottom: '0.4rem' }}>
      <label style={{ color: '#484848', fontSize: '1.1rem', marginRight: '0.5rem' }}>
        {text}
      </label>
      {error && (
        <span style={{ color: 'red', fontSize: '0.9rem' }}>
          {error}
        </span>
      )}
    </div>
  );
  
  const items = Object.entries(cart).map(([engName, count]) => {
    const product = products.find((p) => p.engName === engName);
    return { engName, korName: product?.korName, count };
  });

  return (
    <div
      style={{
        flex: 3,
        backgroundColor: '#e3e3e3',
        borderRadius: '1.5rem',
        boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
        padding: '1.5rem',
        display: 'flex',
        flexDirection: 'column',
        overflowY: 'auto',
        overflowX: 'hidden',
        alignItems: 'center',
        minWidth: '320px',
      }}
    >
      <div style={{ width: '100%', height: '100%', margin: '3% 3% 3% 7%' }}>
        <div
          style={{
            fontSize: '1.8rem',
            fontWeight: 'bold',
            color: '#484848',
            textAlign: 'left',
            marginBottom: '2rem',
          }}
        >
          Summary
        </div>

        <div style={{ marginBottom: '2rem' }}>
          {items.length === 0 && <div style={{ color: '#666' }}>장바구니가 비었습니다.</div>}

          {items.map(({ engName, count }) => (
            <div
              key={engName}
              style={{
                fontSize: '1.2rem',
                fontWeight: '600',
                marginBottom: '0.75rem',
                color: '#484848',
                display: 'flex',
                alignItems: 'center',
              }}
            >
              <div style={{ flex: 5, textAlign: 'left' }}>{engName}</div>
              <div style={{ flex: 1, textAlign: 'center' }}>{count}개</div>
              <div style={{ flex: 1, textAlign: 'center' }}>
                <button
                  onClick={() => onDecrease(engName)}
                  style={{
                    backgroundColor: '#484848',
                    color: 'white',
                    border: 'none',
                    borderRadius: '0.375rem',
                    padding: '0.2rem 0.6rem',
                    cursor: 'pointer',
                    fontWeight: 'bold',
                    fontSize: '1rem',
                  }}
                  title="수량 감소"
                >
                  -
                </button>
              </div>
            </div>
          ))}
        </div>

        {/* 이메일 */}
        {renderLabel('이메일', errors.email)}
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="example@example.com"
          style={{
            height: '2rem',
            width: '90%',
            padding: '0.5rem 0.75rem',
            border: '1px solid transparent',
            borderRadius: '0.5rem',
            marginBottom: '1.5rem',
          }}
        />

        {/* 주소 */}
        {renderLabel('주소', errors.address)}
        <input
          type="text"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          placeholder="서울특별시 ..."
          style={{
            height: '2rem',
            width: '90%',
            padding: '0.5rem 0.75rem',
            border: '1px solid transparent',
            borderRadius: '0.5rem',
            marginBottom: '1.5rem',
          }}
        />

        {/* 상세 주소 */}
        {renderLabel('상세 주소', errors.zipcode)}
        <input
          type="text"
          value={zipcode}
          onChange={(e) => setZipcode(e.target.value)}
          placeholder="00000"
          style={{
            height: '2rem',
            width: '90%',
            padding: '0.5rem 0.75rem',
            border: '1px solid transparent',
            borderRadius: '0.5rem',
            marginBottom: '2rem',
          }}
        />

        <p
          style={{
            fontSize: '0.9rem',
            color: '#484848',
            marginBottom: '1.5rem',
          }}
        >
          ※ 당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
        </p>

        <button
          onClick={handleSubmit}
          style={{
            width: '90%',
            padding: '1rem',
            backgroundColor: '#484848',
            color: 'white',
            borderRadius: '0.5rem',
            fontSize: '1.2rem',
            fontWeight: 'bold',
            border: 'none',
            cursor: 'pointer',
          }}
        >
          결제하기
        </button>
      </div>
    </div>
  );
}
