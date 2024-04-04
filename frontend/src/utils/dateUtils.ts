import { DateType } from "./types";

export const getToday = () => {
  const today = new Date();
  const year: number = today.getFullYear();
  const month: number = today.getMonth() + 1;
  const day: number = today.getDate();
  const date = {
    year,
    month,
    day,
  };
  return date;
};

export const getWeekNumber = (date: DateType) => {
  const inputDate = new Date(`${date.year}-${date.month}-${date.day}`);
  const currentDate = inputDate.getDate();
  const firstDay = new Date(inputDate.setDate(1)).getDay();

  return Math.ceil((currentDate + firstDay) / 7);
};

export const addZero = (number: number): string => {
  if (number < 10) {
    return "0" + number;
  }
  return "" + number;
};

export const getLastSunday = () => {
  const today = new Date();

  const todayDayOfWeek = today.getDay();

  const lastMonday = new Date(today);
  lastMonday.setDate(today.getDate() - todayDayOfWeek - 6); // -6을 더하여 저번 주 월요일로 설정

  const lastSunday = new Date(today);
  lastSunday.setDate(today.getDate() - todayDayOfWeek);

  const year: number = lastSunday.getFullYear();
  const month: string = addZero(lastSunday.getMonth() + 1);
  const day: number = lastSunday.getDate();
  const date = {
    year,
    month,
    day,
  };
  return date;
};
