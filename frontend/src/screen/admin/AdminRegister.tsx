import AdminLogo from "../../components/Admin/AdminLogo";
import AdminRegisterForm from "../../components/Admin/AdminRegisterForm";

export default function AdminRegister() {
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
        {/* 왼쪽 로고 영역 */}
        <AdminLogo
            bottomMessage="Are you a member?"
            linkText="Log in now"
            linkTo="/admin/login"
        />

        {/* 오른쪽 폼 영역 */}
        <AdminRegisterForm />
        </div>
    </div>
  );
}
