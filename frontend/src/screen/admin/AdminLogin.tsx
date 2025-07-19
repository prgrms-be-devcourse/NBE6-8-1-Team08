import React from "react";
import AdminLoginForm from "../../components/AdminLoginForm";
import AdminLogo from "../../components/AdminLogo";

export default function AdminPage() {
  return (
    <div
      style={{
        backgroundColor: "#474747",
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        padding: "2rem",
      }}
    >
      <div
        style={{
          display: "flex",
          width: "900px",
          borderRadius: "20px",
          overflow: "hidden",
          boxShadow: "0 8px 24px rgba(0,0,0,0.15)",
          backgroundColor: "#ededed",
          position: "relative",
        }}
      >
        <AdminLogo />
        <AdminLoginForm />
      </div>
    </div>
  );
}
