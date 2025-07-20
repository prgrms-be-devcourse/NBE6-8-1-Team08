import { useState } from "react";
import axios from "axios";

export default function AdminRegisterForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");

  const [usernameError, setUsernameError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [repeatError, setRepeatError] = useState("");

  const validate = () => {
    let isValid = true;

    if (!username.trim()) {
      setUsernameError("필수 입력값입니다");
      isValid = false;
    } else if (username.length < 4 || username.length > 10) {
      setUsernameError("4~10자리여야 합니다");
      isValid = false;
    } else {
      setUsernameError("");
    }

    if (!password.trim()) {
      setPasswordError("필수 입력값입니다");
      isValid = false;
    } else if (password.length < 8 || password.length > 20) {
      setPasswordError("8~20자리여야 합니다");
      isValid = false;
    } else {
      setPasswordError("");
    }

    if (!repeatPassword.trim()) {
      setRepeatError("필수 입력값입니다");
      isValid = false;
    } else if (repeatPassword !== password) {
      setRepeatError("비밀번호가 일치하지 않습니다");
      isValid = false;
    } else {
      setRepeatError("");
    }

    return isValid;
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!validate()) return;

    try {
      const response = await axios.post("http://localhost:8080/admin/signup", {
        adminId: username,
        inputPassword: password,
        confirmPassword: repeatPassword,
      }, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      alert(response.data.msg);
    } catch (error: any) {
      if (error.response) {
        alert(error.response.data.msg);
      } else {
        alert("서버와 연결할 수 없습니다.");
      }
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
          fontSize: "1.5rem",
          fontWeight: "bold",
          color: "#1f2937",
          marginBottom: "2rem",
          alignSelf: "flex-end",
        }}
      >
        Register with your e-mail
      </h2>

      <div style={{ width: "90%", marginBottom: "0.25rem", alignSelf: "flex-start" }}>
        <span style={{
          color: "#B7B7B7",
          fontWeight: 600,
          fontSize: "0.95rem",
        }}>USERNAME (*)</span>
        {usernameError && (
          <span style={{ color: "red", fontSize: "0.85rem", marginLeft: "0.5rem" }}>
            {usernameError}
          </span>
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

      <div style={{ width: "90%", marginBottom: "0.25rem", alignSelf: "flex-start" }}>
        <span style={{
          color: "#B7B7B7",
          fontWeight: 600,
          fontSize: "0.95rem",
        }}>PASSWORD (*)</span>
        {passwordError && (
          <span style={{ color: "red", fontSize: "0.85rem", marginLeft: "0.5rem" }}>
            {passwordError}
          </span>
        )}
      </div>
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Password"
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

      <div style={{ width: "90%", marginBottom: "0.25rem", alignSelf: "flex-start" }}>
        <span style={{
          color: "#B7B7B7",
          fontWeight: 600,
          fontSize: "0.95rem",
        }}>REPEAT PASSWORD (*)</span>
        {repeatError && (
          <span style={{ color: "red", fontSize: "0.85rem", marginLeft: "0.5rem" }}>
            {repeatError}
          </span>
        )}
      </div>
      <input
        type="password"
        value={repeatPassword}
        onChange={(e) => setRepeatPassword(e.target.value)}
        placeholder="Repeat Password"
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
        Create Account
      </button>
    </form>
  );
}
