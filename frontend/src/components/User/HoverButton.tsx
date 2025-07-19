type HButtonProps = {
  text: string;
  onClick: () => void;
};

export default function HButton({ text, onClick }: HButtonProps) {
  return (
    <div
      onClick={onClick}
      className="w-full h-full cursor-pointer bg-[#ffffff] rounded-[10px]
                 flex items-center justify-center
                 shadow-[0_1px_1px_rgba(0,0,0,0.25)] hover:shadow-[0_4px_4px_rgba(0,0,0,0.25)]
                 transition-shadow duration-300"
    >
      <div className="text-white font-inria-sans text-[20px]" style={{ fontWeight: 750 }}>
        {text}
      </div>
    </div>
  );
}
