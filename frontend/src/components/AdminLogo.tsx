import React from "react";
import { useNavigate } from "react-router-dom";

export default function AdminLogo() {
  const navigate = useNavigate();

  return (
    <div
      style={{
        flex: 1,
        display: "flex",
        flexDirection: "column",
        padding: "3rem",
        position: "relative",
        color: "#1f2937",
      }}
    >
      <h1
        style={{
          fontSize: "1.5rem",
          fontWeight: "600",
          position: "absolute",
          top: "1.5rem",
          left: "3rem",
          margin: 0,
        }}
      >
        Welcome!
      </h1>

      <div
        style={{
          fontSize: "1.1rem",
          color: "#4b5563",
          position: "absolute",
          bottom: "1rem",
          left: "1rem",
        }}
      >
        Not a member yet?{" "}
        <span
          style={{ color: "#3b82f6", cursor: "pointer", textDecoration: "underline" }}
          onClick={() => alert("Register 클릭됨")}
        >
          Register now
        </span>
      </div>

      <div
        style={{
          flexGrow: 1,
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          gap: "0.5rem",
          height: "100%",
          textAlign: "center",
          cursor: "pointer",
        }}
        onClick={() => navigate("/")}
      >
        <div style={{ fontSize: "2.5rem", fontWeight: "700" }}>Grids</div>
        <img
          src="https://i.postimg.cc/XYnmtRLS/logo.png"
          alt="logo"
          style={{ width: "30px", height: "30px", margin: "0.5rem 0" }}
        />
        <div style={{ fontSize: "2.5rem", fontWeight: "700" }}>Circle</div>
      </div>
    </div>
  );
}
