import { useState, ChangeEvent } from "react";

/**
 * @param {string} defaultValue 기본으로 주어지는 데이터 기본은 빈 문자열
 * @param {function} validationFn input value를 검사할 함수
 * @returns {object} state, state 변경함수, 값 유효성 결과
 */
export function useInput(
  defaultValue: string,
  validationFn: (value: string) => boolean
) {
  const [enteredValue, setEnteredValue] = useState<string>(defaultValue);

  function handleInputChange(event: ChangeEvent<HTMLInputElement>) {
    setEnteredValue(event.target.value.trim());
  }

  const valueIsValid = validationFn(enteredValue);

  return {
    value: enteredValue,
    handleInputChange,
    valueIsValid: valueIsValid,
  };
}
