import React, { useState } from "react";
import Calendar from "react-calendar";
import dayjs from "dayjs";
import { styled } from "styled-components";

import "react-calendar/dist/Calendar.css";
import { DateType } from "../../../utils/types";

const StyledCalendarWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  position: relative;
  .react-calendar {
    width: 80%;
    border: none;
    border-radius: 0.5rem;
    box-shadow: 4px 2px 10px 0px rgba(0, 0, 0, 0.13);
    padding: 3% 5%;
    background-color: white;
  }
  .react-calendar,
  .react-calendar *,
  .react-calendar *:before,
  .react-calendar *:after {
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
  .react-calendar__navigation {
    justify-content: center;
  }
  .react-calendar__month-view__weekdays abbr {
    text-decoration: none;
  }
  .react-calendar__navigation__label {
    flex-grow: 0 !important;
  }
  .react-calendar__navigation button:focus {
    background-color: white;
    color: ${(props) => props.theme.darkBlack};
  }
  .react-calendar__navigation button:disabled {
    background-color: white;
    color: ${(props) => props.theme.darkBlack};
  }
  .react-calendar__tile {
    border-radius: 5px;
    position: relative;
    text-align: center;
  }
  .react-calendar__tile--now {
    background: #c5e7ff;
    border-radius: 5px;
    color: #71c3ff;
  }
  .react-calendar__tile--now:hover {
    background: #c5e7ff;
    border-radius: 5px;
    color: #71c3ff;
  }
  .react-calendar__tile--active {
    background: #c5e7ff;
    border-radius: 5px;
    color: black;
    font-weight: bolder;
  }
`;

const StyledCalendar = styled(Calendar)``;
interface CalendarType {
  selectedDate: DateType;
  handleDateChange: (newDate: any) => void;
}

/**
 * @todo 나중에 스타일 손봐야함, 주차별 disable 값도 조정해야함
 */

const ModalCalendar = ({ selectedDate, handleDateChange }: CalendarType) => {
  const date = new Date(
    `${selectedDate.year}-${selectedDate.month}-${selectedDate.day}`
  );
  const today = new Date();

  const todayDayOfWeek = today.getDay();

  const lastMonday = new Date(today);
  lastMonday.setDate(today.getDate() - todayDayOfWeek - 6); // -6을 더하여 저번 주 월요일로 설정

  const lastSunday = new Date(today);
  lastSunday.setDate(today.getDate() - todayDayOfWeek);

  return (
    <StyledCalendarWrapper>
      <StyledCalendar
        value={date}
        onChange={handleDateChange}
        formatDay={(locale, date) => dayjs(date).format("DD")}
        showNeighboringMonth={false}
        maxDate={lastSunday}
        next2Label={null}
        prev2Label={null}
      />
    </StyledCalendarWrapper>
  );
};

export default ModalCalendar;
