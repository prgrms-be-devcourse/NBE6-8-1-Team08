import HButton from './HoverButton';
import { useNavigate } from 'react-router-dom';

interface ButtonInfo {
  text: string;
  url: string;
  className?: string; 
}

interface MainBackboardProps {
  buttons: ButtonInfo[];
}

export default function MainBackboard({ buttons }: MainBackboardProps) {
  const navigate = useNavigate();

  return (
    <div className="relative h-[60vh] w-1/2 bg-[#ededed] rounded-[20px] p-8 min-w-[500px]">
      <div 
         onClick={() => navigate('/')}
        className="flex justify-center items-center cursor-pointer text-[48px] h-[140px]" style={{ gap: '1rem' }}>
        <span style={{ fontWeight: 750 }}>Grids</span>
        <img
          src="https://i.postimg.cc/XYnmtRLS/logo.png"
          className="w-[40px] h-[40px] relative top-[2px]"
          alt="logo"
        />
        <span style={{ fontWeight: 750 }}>Circle</span>
      </div>



      {buttons.map((btn, idx) => (
        <div
          key={idx}
          className={`absolute left-1/2 -translate-x-1/2 ${btn.className || 'w-[60%] h-[15%] min-w-[200px]'}`}
          style={{ top: `${40 + idx * 25}%` }}
        >
          <HButton text={btn.text} onClick={() => navigate(btn.url)} />
        </div>
      ))}
    </div>
  );
}
