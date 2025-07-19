import React, { useState } from "react";

export default function AdminRegisterForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");

  return (
    <form
      onSubmit={(e) => {
        e.preventDefault();
        alert("회원가입 시도");
      }}
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

      {/* USERNAME */}
      <div
        style={{
          color: "#B7B7B7",
          fontWeight: "600",
          alignSelf: "flex-start",
          marginBottom: "0.25rem",
          fontSize: "0.95rem",
        }}
      >
        USERNAME (*)
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
          color: "#B7B7B7",
          fontWeight: "600",
          alignSelf: "flex-start",
          marginBottom: "0.25rem",
          fontSize: "0.95rem",
        }}
      >
        PASSWORD (*)
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

      {/* REPEAT PASSWORD */}
      <div
        style={{
          color: "#B7B7B7",
          fontWeight: "600",
          alignSelf: "flex-start",
          marginBottom: "0.25rem",
          fontSize: "0.95rem",
        }}
      >
        REPEAT PASSWORD (*)
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
