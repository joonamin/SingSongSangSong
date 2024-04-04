import { ResponsiveBar } from "@nivo/bar";

const data = [
  {
    title: "노래 1",
    watched: 15,
  },
  {
    title: "노래 2",
    watched: 55,
  },
  {
    title: "노래 3",
    watched: 152,
  },
];

type BarChartType = {
  option: string;
};

const BarChart = ({ option }: BarChartType) => {
  return (
    <div style={{ width: "100%", height: "100%", margin: "0 auto" }}>
      <ResponsiveBar
        data={data}
        keys={["watched"]}
        indexBy="title"
        margin={{ top: 30, right: 130, bottom: 50, left: 60 }}
        padding={0.3}
        valueScale={{ type: "linear" }}
        indexScale={{ type: "band", round: true }}
        colors={["#22A2FF", "#71C3FF", "#B1DEFF"]}
        colorBy="id"
        borderColor={{
          from: "color",
          modifiers: [["darker", 1.6]],
        }}
        axisTop={null}
        axisRight={null}
        axisBottom={{
          tickSize: 5,
          tickPadding: 5,
          tickRotation: 0,
          // 아래 부분에 선택된 tag값을 가져와서 표시해주어야함
          // legend -> 아래 legend text
          legend: option,
          legendPosition: "middle",
          legendOffset: 32,
          truncateTickAt: 0,
        }}
        axisLeft={{
          tickSize: 5,
          tickPadding: 5,
          tickRotation: 0,
          legend: "조회수",
          legendPosition: "middle",
          legendOffset: -40,
          truncateTickAt: 0,
        }}
        labelSkipWidth={12}
        labelSkipHeight={12}
        labelTextColor={{
          from: "color",
          modifiers: [["darker", 1.6]],
        }}
        legends={[
          {
            dataFrom: "keys",
            anchor: "bottom-right",
            direction: "column",
            justify: false,
            translateX: 120,
            translateY: 0,
            itemsSpacing: 2,
            itemWidth: 100,
            itemHeight: 20,
            itemDirection: "left-to-right",
            itemOpacity: 0.85,
            symbolSize: 20,
            effects: [
              {
                on: "hover",
                style: {
                  itemOpacity: 1,
                },
              },
            ],
          },
        ]}
        motionConfig="wobbly"
        role="application"
        ariaLabel="Nivo bar chart demo"
        barAriaLabel={(e) =>
          e.id + ": " + e.formattedValue + " in country: " + e.indexValue
        }
      />
    </div>
  );
};

export default BarChart;
