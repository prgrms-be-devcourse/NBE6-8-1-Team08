export default function OrderList({ products, onAdd }) {
    return (
      <div
        style={{
          flex: 7,
          backgroundColor: '#ffffff',
          borderRadius: '1.5rem',
          boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
          padding: '1.5rem',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          minWidth: '320px',
        }}
      >
        <div style={{ width: '100%', maxWidth: '1024px', margin: '0 5%' }}>
          <div
            style={{
              fontSize: '1.8rem',
              fontWeight: 'bold',
              color: '#484848',
              textAlign: 'left',
              marginBottom: '1.5rem',
            }}
          >
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
                  onClick={() => onAdd(product)}
                  onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = '#3a3a3a')}
                  onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = '#484848')}
                >
                  추가
                </button>
              </div>
            ))}
          </div>
        </div>
      </div>
    );
  }
  