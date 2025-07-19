// UserMain.tsx

import MainBackboard from '../../components/MainBackboard';

export default function UserMain() {
  const buttons = [
    { text: '주문하기', url: '/user/menu' },
    { text: '내 주문 조회하기', url: '/admin' }
  ];

  return (
    <div className="flex justify-center items-center h-screen bg-[#474747]">
      <MainBackboard buttons={buttons} />
    </div>
  );
}
