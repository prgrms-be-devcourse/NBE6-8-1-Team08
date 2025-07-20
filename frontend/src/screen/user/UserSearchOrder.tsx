import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function UserSearchOrder() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [isValidEmail, setIsValidEmail] = useState(true);

  const validateEmail = (email: string) => {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  };

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setEmail(value);
    setIsValidEmail(validateEmail(value) || value.trim() === "");
  };

  const handleSubmit = async () => {
    if (!email.trim()) {
      alert("이메일을 입력해주세요.");
      return;
    }

    if (!validateEmail(email)) {
      setIsValidEmail(false);
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/orders/user/findorder?email=${encodeURIComponent(email.trim())}`
        // 만약 백엔드가 `arg0`을 요구한다면 아래로 변경:
        // `http://localhost:8080/orders/user/findorder?arg0=${encodeURIComponent(email.trim())}`
      );

      if (!response.ok) {
        throw new Error("조회 실패");
      }

      const data = await response.json(); // 필요 시 응답 데이터 활용

      navigate(`/user/orderlist?username=${encodeURIComponent(email.trim())}`);
    } catch (error) {
      alert("해당 이메일로 주문을 찾을 수 없습니다.");
      console.error(error);
    }
  };

  return (
    <div className="flex justify-center items-center h-screen bg-[#474747]">
      <div className="relative h-[60vh] w-1/2 bg-[#ededed] rounded-[20px] p-8 min-w-[500px]">
        {/* 로고 */}
        <div
          onClick={() => navigate("/")}
          className="flex justify-center items-center cursor-pointer text-[48px] h-[140px]"
          style={{ gap: "1rem" }}
        >
          <span style={{ fontWeight: 750 }}>Grids</span>
          <img
            src="https://i.postimg.cc/XYnmtRLS/logo.png"
            className="w-[40px] h-[40px] relative top-[2px]"
            alt="logo"
          />
          <span style={{ fontWeight: 750 }}>Circle</span>
        </div>

        {/* 이메일 입력창 */}
        <div
          className="absolute left-1/2 -translate-x-1/2 w-[60%] min-w-[300px]"
          style={{ top: "45%" }}
        >
          <div
            style={{
              display: "flex",
              alignItems: "center",
              gap: "0.5rem",
              marginBottom: "0.5rem",
            }}
          >
            <label
              htmlFor="email-input"
              style={{
                color: "#040404",
                fontWeight: "600",
                display: "block",
                flexShrink: 0,
              }}
            >
              자신의 email을 입력해 주세요
            </label>
            {!isValidEmail && (
              <span
                style={{
                  color: "red",
                  fontWeight: "normal",
                  fontSize: "0.9rem",
                }}
              >
                email 형식 지켜주세요
              </span>
            )}
          </div>
          <input
            id="email-input"
            type="email"
            value={email}
            onChange={handleEmailChange}
            placeholder="example@gmail.com"
            style={{
              width: "100%",
              height: "3rem",
              padding: "0.5rem",
              borderRadius: "0.5rem",
              border: isValidEmail ? "1px solid #ccc" : "1px solid red",
              fontWeight: 600,
              fontSize: "1rem",
              color: "#040404",
              outline: "none",
              marginBottom: "1rem",
              boxSizing: "border-box",
            }}
          />
        </div>

        {/* 조회하기 버튼 */}
        <div className="absolute" style={{ bottom: "1.5rem", right: "1.5rem" }}>
          <button
            onClick={handleSubmit}
            style={{
              backgroundColor: "#000000",
              color: "#ffffff",
              width: "60px",
              height: "60px",
              borderRadius: "12px",
              border: "none",
              cursor: "pointer",
              fontSize: "24px",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              userSelect: "none",
              lineHeight: 1,
            }}
            aria-label="조회하기"
            onMouseOver={(e) =>
              (e.currentTarget.style.backgroundColor = "#222222")
            }
            onMouseOut={(e) =>
              (e.currentTarget.style.backgroundColor = "#000000")
            }
          >
            &rarr;
          </button>
        </div>
      </div>
    </div>
  );
}
