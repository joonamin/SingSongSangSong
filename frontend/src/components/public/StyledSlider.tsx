import React, { ReactNode } from "react";
import styled from "styled-components";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import styles from "./StyledSlider.module.css";

const MySlider = styled(Slider)`
  .slick-cloned.slick-center {
    transform: scale(1) !important;
  }
  .slick-slide {
    height: 200px !important;
    width: 200px !important;
  }
  .slick-list {
    marign-right: -20px;
  }
  .slick-track {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: stretch;
    gap: 4rem;
  }
  .slick-prev {
    z-index: 1;
    left: 30px;
  }
  .slick-next {
    right: 40px;
  }
  .slick-prev:before,
  .slick-next:before {
    font-size: 30px;
    opacity: 0.5;
    color: black;
  }
  .slick-dots {
    display: flex;
    justify-content: center;
    bottom: 30px;
    color: black;

    li button:before {
      color: black;
    }

    li.slick-active button:before {
      color: black;
    }
  }
`;

type ComponentProps = {
  children: ReactNode;
};

const StyledSlider = ({ children }: ComponentProps) => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 4,
    slidesToScroll: 1,
    initialSlide: 1,
    autoplay: true,
    autoplaySpeed: 2000,
    useTransform: false,
  };
  return (
    <div className={`${styles.container}`}>
      <MySlider {...settings}>{children}</MySlider>
    </div>
  );
};

export default StyledSlider;
