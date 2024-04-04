import React, { ComponentProps, ReactNode } from "react";

import styles from "./Button.module.css";

interface ButtonType extends ComponentProps<"button"> {
  children?: ReactNode;
  disabled?: boolean;
}

const Button: React.FC<ButtonType> = ({
  children,
  disabled = false,
  ...rest
}) => {
  return (
    <button disabled={disabled} className={`${styles.base} `} {...rest}>
      {children}
    </button>
  );
};

export default Button;
