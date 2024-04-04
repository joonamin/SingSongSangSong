import React, { ComponentProps, ReactNode, useRef, useEffect } from "react";
import { createPortal } from "react-dom";

interface DialogType extends ComponentProps<"dialog"> {
  children: ReactNode;
  open: boolean;
  onClose: () => void;
}

/**
 * @param open 모달을 열고 닫을 state
 * @param onClose 함수는 open을 false로 바꾸는 setState 함수
 * @param children 표시할 자식 컴포넌트
 */
const Modal: React.FC<DialogType> = ({ children, open, onClose }) => {
  const dialog = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    if (dialog.current && open) {
      dialog.current.showModal();
    } else if (dialog.current && !open) {
      dialog.current.close();
    }
  }, [open]);

  return createPortal(
    <dialog className="modal" ref={dialog} onClose={onClose}>
      {children}
    </dialog>,
    document.getElementById("modal") as HTMLElement
  );
};

export default Modal;
