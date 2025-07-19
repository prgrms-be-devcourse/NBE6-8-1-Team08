import React, { useState } from "react";

export default function AdminLoginForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors: { [key: string]: string } = {};

    if (!username.trim()) {
      newErrors.username = "필수 입력값입니다";
    }
    if (!password.trim()) {
      newErrors.password = "필수 입력값입니다";
    }
    if (!repeatPassword.trim()) {
      newErrors.repeatPassword = "필수 입력값입니다";
    }
    if (password && repeatPassword && password !== repeatPassword) {
      newErrors.repeatPassword = "비밀번호가 일치하지 않습니다";
    }

    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      alert("로그인 시도");
      // 실제 로그인 처리 코드
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{
        backgroundColor: "#ffffff",
        flex: 1,
        padding: "3rem",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <h2
        style={{
          fontSize: "2rem",
          fontWeight: "bold",
          color: "#1f2937",
          marginBottom: "2rem",
          alignSelf: "flex-end",
        }}
      >
        LOG IN
      </h2>

      {/* USERNAME */}
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          width: "90%",
          marginBottom: "0.25rem",
        }}
      >
        <div
          style={{
            color: "#B7B7B7",
            fontWeight: "600",
            fontSize: "0.95rem",
          }}
        >
          USERNAME
        </div>
        {errors.username && (
          <div style={{ color: "red", fontSize: "0.85rem", fontWeight: "600" }}>
            {errors.username}
          </div>
        )}
      </div>
      <input
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="Enter the username"
        style={{
          height: "2.5rem",
          width: "90%",
          padding: "0.5rem 0.75rem",
          border: "1px solid #ccc",
          borderRadius: "0.5rem",
          marginBottom: "1.5rem",
          fontSize: "1rem",
          color: "#333",
        }}
      />

      {/* PASSWORD */}
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          width: "90%",
          marginBottom: "0.25rem",
        }}
      >
        <div
          style={{
            color: "#B7B7B7",
            fontWeight: "600",
            fontSize: "0.95rem",
          }}
        >
          PASSWORD
        </div>
        {errors.password && (
          <div style={{ color: "red", fontSize: "0.85rem", fontWeight: "600" }}>
            {errors.password}
          </div>
        )}
      </div>
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Enter the password"
        style={{
          height: "2.5rem",
          width: "90%",
          padding: "0.5rem 0.75rem",
          border: "1px solid #ccc",
          borderRadius: "0.5rem",
          marginBottom: "1.5rem",
          fontSize: "1rem",
          color: "#333",
          backgroundColor: "white",
        }}
      />

      {/* REPEAT PASSWORD */}
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          width: "90%",
          marginBottom: "0.25rem",
        }}
      >
        <div
          style={{
            color: "#B7B7B7",
            fontWeight: "600",
            fontSize: "0.95rem",
          }}
        >
          REPEAT PASSWORD
        </div>
        {errors.repeatPassword && (
          <div style={{ color: "red", fontSize: "0.85rem", fontWeight: "600" }}>
            {errors.repeatPassword}
          </div>
        )}
      </div>
      <input
        type="password"
        value={repeatPassword}
        onChange={(e) => setRepeatPassword(e.target.value)}
        placeholder="Repeat the password"
        style={{
          height: "2.5rem",
          width: "90%",
          padding: "0.5rem 0.75rem",
          border: "1px solid #ccc",
          borderRadius: "0.5rem",
          marginBottom: "2rem",
          fontSize: "1rem",
          color: "#333",
          backgroundColor: "white",
        }}
      />

      <button
        type="submit"
        style={{
          backgroundColor: "#000",
          color: "#fff",
          width: "90%",
          padding: "0.75rem 0",
          borderRadius: "0.5rem",
          fontWeight: "700",
          fontSize: "1.1rem",
          cursor: "pointer",
          border: "none",
        }}
      >
        Log In
      </button>
    </form>
  );
}
