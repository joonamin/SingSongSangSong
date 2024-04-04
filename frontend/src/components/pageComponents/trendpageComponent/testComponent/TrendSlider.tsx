import React, { ReactNode } from "react";
import styled from "styled-components";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import styles from "./t.module.css";

const MySlider = styled(Slider)`
  .slick-prev {
    z-index: 1;
    left: -50px;
  }

  .slick-next {
    right: -40px;
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
    color: white;

    li button:before {
      color: white;
    }

    li.slick-active button:before {
      color: white;
    }
  }
`;

type ComponentProps = {
  children: ReactNode;
};

const TrendSlider = ({ children }: ComponentProps) => {
  const settings = {
    dots: true,
    fade: true,
    infinite: true,
    speed: 1000,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  return <MySlider {...settings}>{children}</MySlider>;
};

export default TrendSlider;
