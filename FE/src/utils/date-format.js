import dateFns from "date-fns";
import koLocale from "date-fns/locale/ko";

const daysOfWeekListENG = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
const daysOfWeekListKR = ["일", "월", "화", "수", "목", "금", "토"];

function getDateFormat(
  date,
  format = "YYYY.MM.DD(eee) HH:mm",
  addDays = 0,
  subDays = null,
  from = ""
) {
  if (!date) {
    return "";
  }
  let result = dateFns.format(
    subDays ? dateFns.subDays(date, subDays) : dateFns.addDays(date, addDays),
    format,
    { locale: koLocale }
  );

  if (format.includes("eee")) {
    result = result.replace(
      "eee",
      getDayKR(
        subDays
          ? dateFns.subDays(date, subDays)
          : dateFns.addDays(date, addDays)
      )
    );
  }
  return result;
}

function getTimestamp(
  toDate,
  fromDate = new Date(),
  format = "YYYY-MM-DD HH:mm"
) {
  const millisceonds = fromDate - toDate;
  const seconds = Math.round(millisceonds / 1000);
  const minutes = Math.round(seconds / 60);

  if (minutes < 60) {
    return minutes + "분 전";
  }
  const hours = Math.round(minutes / 60);
  if (hours < 24) {
    return hours + "시간 전";
  }
  return getDateFormat(toDate, format);
}

function getDDay(toDate, fromDate = new Date()) {
  const fromDay = new Date(
    fromDate.getFullYear(),
    fromDate.getMonth(),
    fromDate.getDate()
  );
  fromDay.setHours(0, 0, 0, 0);
  const d = new Date(toDate);
  d.setHours(0, 0, 0, 0);
  const milliseconds = d - fromDay;
  if (milliseconds < 0) {
    return 0;
  }
  const seconds = Math.round(milliseconds / 1000);
  const minutes = Math.round(seconds / 60);
  const hours = Math.round(minutes / 60);
  return Math.round(hours / 24);
}

function getDayOfWeekAlphabet3(date) {
  const dayOfWeek = date.getDay();
  return daysOfWeekListENG[dayOfWeek];
}

function getDayKR(date) {
  return daysOfWeekListKR[dateFns.getDay(new Date(date))];
}

function getDayListKR() {
  return daysOfWeekListKR;
}

function getDayOfWeekIndex(weekDays) {
  const result = [];
  weekDays.forEach((weekday) => {
    result.push(daysOfWeekListENG.indexOf(weekday));
  });
  return result;
}

function getNextSeriesDate(salesItem) {
  // 오늘 이후 / 연재 요일 중 / 연재 강의 없는 날짜 반환
  let isFind = false;

  // 연재 강의 날짜
  const lectureDateList = salesItem.items.map((item) => {
    return item.lectureDate;
  });
  // 연재 요일
  const seriesWeekDayList = getDayOfWeekIndex(salesItem.weekday);

  // 비교 대상 날짜
  let date = new Date();
  // isSameDay(date1, date2)
  while (!isFind) {
    if (
      seriesWeekDayList.indexOf(date.getDay()) !== -1 &&
      lectureDateList.filter((lectureDate) => {
        return dateFns.isSameDay(date, lectureDate);
      }).length > 0
    ) {
      isFind = true;
      return date;
    } else {
      date = dateFns.addDays(date, 1);
    }
  }
  return false;
}

function getHourList() {
  let list = [];
  for (let num = 0; num < 24; num++) {
    if (num < 10) {
      list.push("0" + num);
    } else {
      list.push(num.toString());
    }
  }
  return list;
}

function getMinList() {
  let list = [];
  for (let num = 0; num < 60; num++) {
    if (num < 10) {
      list.push("0" + num);
    } else {
      list.push(num.toString());
    }
  }
  return list;
}

function getDayLeft(earlierDay, laterDay) {
  if (isBeforeOrSameDay(earlierDay, laterDay)) {
    // later day 먼저 넣어야 함
    return dateFns.differenceInCalendarDays(
      new Date(laterDay),
      new Date(earlierDay)
    );
  } else {
    return -1;
  }
}

function isBeforeOrSameDay(earlierDay, laterDay) {
  return (
    dateFns.isBefore(new Date(earlierDay), new Date(laterDay)) ||
    dateFns.isSameDay(new Date(earlierDay), new Date(laterDay))
  );
}

function isBeforeDay(earlierDay, laterDay) {
  if (!earlierDay || !laterDay) return false;
  return dateFns.isBefore(earlierDay, laterDay);
}

function isBeforeDttm(a, b) {
  return new Date(a) - new Date(b) < 0;
}

function isAfterDttm(a, b) {
  return new Date(a) - new Date(b) > 0;
}

function setHours(date, hour) {
  return dateFns.setHours(new Date(date), hour);
}

function isSameDay(a, b) {
  return dateFns.isSameDay(a, b);
}

function addDays(date, days) {
  return dateFns.addDays(date, days);
}

function withinInterval(date, interval) {
  return dateFns.isWithinRange(
    new Date(date),
    new Date(interval.start),
    new Date(interval.end)
  );
}

function getDateOfLastDayOfWeek(date, dayIndex) {
  const lastWeekDay = dateFns.format(dateFns.addDays(date, -7));
  const dayList = dateFns.eachDay(lastWeekDay, date);
  const resultDayIndex = dayList.findIndex((day) => {
    return dateFns.getDay(day) === dayIndex;
  });
  return getDateFormat(dayList[resultDayIndex], "YY.MM.DD (eee)");
}

function getDateOfNextDayOfWeek(date, dayIndex) {
  const nextWeekDay = dateFns.format(dateFns.addDays(date, 7));
  const dayList = dateFns.eachDay(date, nextWeekDay);
  const resultDayIndex = dayList.findIndex((day) => {
    return dateFns.getDay(day) === dayIndex;
  });
  return getDateFormat(dayList[resultDayIndex], "YYYY-MM-DD (eee)");
}

function nextDay(date, day) {
  const map = genMap(day);
  return dateFns.addDays(new Date(date), map[dateFns.getDay(new Date(date))]);
}

function genMap(daysToMove) {
  const baseMap = [7, 6, 5, 4, 3, 2, 1];
  if (daysToMove === 0) {
    return baseMap;
  } else {
    const mapStart = baseMap.slice(-daysToMove);
    const mapEnd = baseMap.slice(0, baseMap.length - daysToMove);
    return mapStart.concat(mapEnd);
  }
}
// only yyyy.mm.dd hh:mm
function koTimeAmPm(date, type) {
  return getDateFormat(date, "YYYY.MM.DD HH:mm");
}
/**
 * 결제일시를 기준으로 다음 결제일 가져온다.
 * 29, 30, 31일 인 경우 익월의 마지막날을 가져온다.
 * @param {*} params
 */
const getNextPaymentDttm = (params) => {
  const { paymentDttm, paymentDay, planCycle } = params;
  let next;
  if (planCycle === "ANNUALY") {
    next = new Date(paymentDttm.getFullYear(), paymentDttm.getMonth() + 13, 0);
  } else {
    next = new Date(paymentDttm.getFullYear(), paymentDttm.getMonth() + 2, 0);
  }
  let result;
  // 다음달 마지막날이 paymentDay 보다 크거나 같은 경우
  if (paymentDay <= next.getDate()) {
    result = new Date(paymentDttm);
    let plus = planCycle === "ANNUALY" ? 12 : 1;
    result.setMonth(result.getMonth() + plus);
    result.setDate(paymentDay);
  } else {
    result = next;
    result.setHours(paymentDttm.getHours());
    result.setMinutes(paymentDttm.getMinutes());
    result.setSeconds(paymentDttm.getSeconds());
  }
  return result;
};

// api 호출 시 사용하는 시작일 , 종료일  date 변환 함수
// isStart : true(startDate: 시작일인경우) or false(endDate: 종료일인경우)
const toJsonDateFormat = (date, isStart) => {
  const setDate = new Date(date);
  if (isStart) {
    const sDate = setDate.toJSON();
    return sDate; //ex) YYYY-MM-DDT00:00:00.000Z
  } else {
    setDate.setUTCHours(23, 59, 59, 999);
    const eDate = setDate.toJSON();
    return eDate; //ex) YYYY-MM-DDT23:59:59.999Z
  }
};

// https://date-fns.org/v2.29.3/docs/differenceInMinutes
const differenceInMinutes = (
  dateLeft = new Date(),
  dateRight = new Date(),
  options // { roundingMethod: ceil, floor, round or trunc }
) => {
  return dateFns.differenceInMinutes(
    new Date(dateLeft),
    new Date(dateRight),
    options
  );
};

export {
  getDateFormat,
  getTimestamp,
  getDDay,
  getDayOfWeekAlphabet3,
  getDayOfWeekIndex,
  getNextSeriesDate,
  getDayKR,
  getDayListKR,
  getHourList,
  getMinList,
  getDayLeft,
  isBeforeOrSameDay,
  isBeforeDttm,
  setHours,
  isAfterDttm,
  isSameDay,
  isBeforeDay,
  withinInterval,
  addDays,
  getDateOfLastDayOfWeek,
  getDateOfNextDayOfWeek,
  nextDay,
  daysOfWeekListENG,
  koTimeAmPm,
  getNextPaymentDttm,
  toJsonDateFormat,
  differenceInMinutes,
};
