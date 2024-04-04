import React, { ComponentProps } from "react";
import styles from "./AuthInput.module.css";

interface AuthInputType extends ComponentProps<"input"> {
  id: string;
  label: string;
  name: string;
  hasError?: boolean | string;
}
/**
 * @param label input태그 위에 나올 label
 * @param id input의 id태그
 * @param name input의 name태그
 * @param hasError input이 잘못된 경우 error메시지 출력용 boolean value
 * @param props input에 넣을 속성값들 (ex-> disable)
 */
const AuthInput: React.FC<AuthInputType> = ({
  id,
  label,
  name,
  hasError,
  ...props
}) => {
  return (
    <div className={styles.container}>
      <label htmlFor={id}>{label}</label>
      <input id={id} name={name} {...props} />
      {hasError && <p>{hasError}</p>}
    </div>
  );
};

export default AuthInput;
