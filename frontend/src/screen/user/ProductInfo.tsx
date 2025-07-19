export default function ProductInfo() {
  const products = [
    { korName: '커피콩', engName: 'Columbia Bean', price: '50000', imgUrl: 'https://via.placeholder.com/80' },
    { korName: '커피콩', engName: 'Ethiopia Yirgacheffe', price: '45000', imgUrl: 'https://via.placeholder.com/80' },
    { korName: '커피콩', engName: 'Guatemala Antigua', price: '52000', imgUrl: 'https://via.placeholder.com/80' },
    { korName: '커피콩', engName: 'Guatemala Antigua', price: '52000', imgUrl: 'https://via.placeholder.com/80' },
    { korName: '커피콩', engName: 'Guatemala Antigua', price: '52000', imgUrl: 'https://via.placeholder.com/80' },
    { korName: '커피콩', engName: 'Guatemala Antigua', price: '52000', imgUrl: 'https://via.placeholder.com/80' },
    { korName: '커피콩', engName: 'Guatemala Antigua', price: '52000', imgUrl: 'https://via.placeholder.com/80' }
  ];

  return (
    <div style={{ minHeight: '100vh', minWidth: '1500px', backgroundColor: '#DCDCDC', display: 'flex', flexDirection: 'column', padding: '2rem' }}>
      
      <div style={{ height: '15vh', display: 'flex', alignItems: 'center', justifyContent: 'center', marginBottom: '2rem' }}>
        <div style={{ fontSize: '3rem', fontWeight: 'bold', color: '#484848' }}>Grids & Circle</div>
      </div>

      <div style={{ display: 'flex', flex: 1, gap: '2rem' }}>
        <div style={{
          flex: 7,
          backgroundColor: '#ffffff',
          borderRadius: '1.5rem',
          boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
          padding: '1.5rem',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          minWidth: '320px',
        }}>
          <div style={{ width: '100%', maxWidth: '1024px', margin: '0 5%' }}>
            <div style={{
              fontSize: '1.8rem',
              fontWeight: 'bold',
              color: '#484848',
              textAlign: 'left',
              marginBottom: '1.5rem',
            }}>
              주문목록
            </div>

            <div style={{ maxHeight: '600px', overflowY: 'auto' }}>
              {products.map((product, idx) => (
                <div
                  key={idx}
                  style={{
                    border: '1px solid rgba(0, 0, 0, 0.1)',
                    borderRadius: '1rem',
                    margin: '3% 3% 3% 5%',
                    padding: '1.5rem',
                    height: '25%',
                    width: '80%',
                    display: 'flex',
                    alignItems: 'center',
                  }}
                >
                  <img
                    src={product.imgUrl}
                    alt={product.engName}
                    style={{
                      width: '80px',
                      height: '80px',
                      borderRadius: '0.75rem',
                      objectFit: 'cover',
                      marginRight: '3rem',
                      flexShrink: 0,
                    }}
                  />
                  <div
                    style={{
                      display: 'flex',
                      flexDirection: 'column',
                      flexGrow: 1,
                      marginRight: '3rem',
                    }}
                  >
                    <span style={{ color: '#6b7280', fontSize: '1.125rem' }}>{product.korName}</span>
                    <span style={{ color: '#484848', fontSize: '1.5rem', fontWeight: 'bold' }}>
                      {product.engName}
                    </span>
                  </div>
                  <div
                    style={{
                      fontSize: '1.25rem',
                      fontWeight: '600',
                      color: '#484848',
                      marginRight: '4rem',
                      whiteSpace: 'nowrap',
                    }}
                  >
                    {Number(product.price).toLocaleString()}원
                  </div>
                  <button
                    style={{
                      backgroundColor: '#484848',
                      color: 'white',
                      borderRadius: '0.375rem',
                      padding: '0.75rem 2rem',
                      fontSize: '1.125rem',
                      minWidth: '100px',
                      whiteSpace: 'nowrap',
                      transition: 'background-color 0.3s',
                      border: 'none',
                      cursor: 'pointer',
                    }}
                    onMouseEnter={e => (e.currentTarget.style.backgroundColor = '#3a3a3a')}
                    onMouseLeave={e => (e.currentTarget.style.backgroundColor = '#484848')}
                  >
                    추가
                  </button>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Summary 영역 */}
        <div style={{
          flex: 3,
          backgroundColor: '#e3e3e3',
          borderRadius: '1.5rem',
          boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
          padding: '1.5rem',
          display: 'flex',
          flexDirection: 'column',
          overflowY: 'auto',
          alignItems: 'center',
          minWidth: '320px',
        }}>
          <div style={{ width: '100%', height: '100%', margin: '3% 3% 3% 7%' }}>
            <div style={{
              fontSize: '1.8rem',
              fontWeight: 'bold',
              color: '#484848',
              textAlign: 'left',
              marginBottom: '2rem',
            }}>
              Summary
            </div>

            <label style={{ color: '#484848', fontSize: '1.1rem', margin: '0 0 0.4rem 0', display: 'block' }}>이메일</label>
            <input
              type="email"
              placeholder="example@example.com"
              style={{
                width: '100%',
                padding: '0.5rem 0.75rem',
                border: '1px solid transparent',
                borderRadius: '0.5rem',
                marginBottom: '1.5rem',
              }}
            />

            <label style={{ color: '#484848', fontSize: '1.1rem', margin: '0 0 0.4rem 0', display: 'block' }}>주소</label>
            <input
              type="text"
              placeholder="서울특별시 ..."
              style={{
                width: '100%',
                padding: '0.5rem 0.75rem',
                border: '1px solid transparent',
                borderRadius: '0.5rem',
                marginBottom: '1.5rem',
              }}
            />

            <label style={{ color: '#484848', fontSize: '1.1rem', margin: '0 0 0.4rem 0', display: 'block' }}>우편번호</label>
            <input
              type="text"
              placeholder="00000"
              style={{
                width: '100%',
                padding: '0.5rem 0.75rem',
                border: '1px solid transparent',
                borderRadius: '0.5rem',
                marginBottom: '2rem',
              }}
            />

            <p style={{
              fontSize: '0.9rem',
              color: '#484848',
              marginBottom: '1.5rem',
            }}>
              ※ 당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
            </p>

            <button style={{
              width: '100%',
              padding: '1rem',
              backgroundColor: '#484848',
              color: 'white',
              borderRadius: '0.5rem',
              fontSize: '1.2rem',
              fontWeight: 'bold',
              border: 'none',
              cursor: 'pointer',
            }}>
              결제하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
