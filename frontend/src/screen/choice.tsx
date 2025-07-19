import { useNavigate } from 'react-router-dom';
import HButton from '../components/HoverButton';
import React from 'react';


export default function Choice() {
  const navigate = useNavigate();

  return (
    <div className="w-full h-screen bg-[#474747] flex justify-center items-center p-8">
      <div className="relative h-[80vh] w-3/5 bg-[#ffffff] rounded-[20px] p-8">
        <div className="absolute left-1/2 -translate-x-1/2 top-[20%] w-[30%] h-[10%] flex items-center justify-center text-black font-inter font-bold text-[32px]">
          임시 이동 페이지
        </div>

        <div className="absolute left-1/2 -translate-x-1/2 top-[40%] w-[30%] h-[10%]">
          <HButton text="유저 페이지" onClick={() => navigate('/user')} />
        </div>

        <div className="absolute left-1/2 -translate-x-1/2 top-[60%] w-[30%] h-[10%]">
          <HButton text="관리자 페이지" onClick={() => navigate('/admin')} />
        </div>
      </div>
    </div>
  );
}
