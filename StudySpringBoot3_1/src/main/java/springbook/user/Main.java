package springbook.user;

import java.util.*;

public class Main {
    /*
1.  1~n 분류되는 개인정보 n개
1.1 각 약관마다 개인정보 보관 유효기간이 정해져 있다.
1.2 당신은 각 개인정보가 어떤 약관으로 수집됐는지 알고 있습니다.
1.3 수집된 개인정보는 유효기간 전까지만 보관 가능하며,
1.4 유효기간이 지났다면 반드시 파기해야 합니다.

    예를 들어, A라는 약관의 유효기간이 12 달이고, 2021년 1월 5일에 수집된 개인정보가 A약관으로 수집되었다면 해당 개인정보는 2022년 1월 4일까지 보관 가능하며 2022년 1월 5일부터 파기해야 할 개인정보입니다.
    1. 유효기간 12달
    2. 2021년 1월 5일에 수집
    3. 2022년 1월 4일까지 보관 가능
    4. 2022년 1월 5일에 파기
    당신은 오늘 날짜로 파기해야 할 개인정보 번호들을 구하려 합니다.

    모든 달은 28일까지 있다고 가정합니다.
    if 모든 달은 28일까지 있다.

    다음은 오늘 날짜가 2022.05.19일 때의 예시입니다.
    */

    public static void main(String[] args) {
        String today = "2022.05.19";
        String[] terms = {"A 6", "B 12", "C 3"};
        String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};

        Solution solution = new Solution();
        solution.solution(today, terms, privacies);
    }
}

class Solution {
    public int[] solution(String today, String[] terms, String[] privacies) {
        Map<String, Integer> termDic = new LinkedHashMap<>();
        List<Integer> anwerList = new ArrayList<>();
        int todayInt = Integer.parseInt(today.replace(".", ""));

        for (String term : terms) {
            String[] termArr = term.split(" ");
            String type = termArr[0];
            Integer duti = Integer.valueOf(termArr[1]);
            // {"A 6", "B 12", "C 3"};
            termDic.put(type, duti);
        }


        for (int i = 0; i < privacies.length; i++) {
            String[] privacieTallArr = privacies[i].split(" ");
            String[] privacieArr = privacieTallArr[0].split("\\.");
            String type = privacieTallArr[1];
            int yearPrivacie = Integer.parseInt(privacieArr[0]);
            int monthPrivacie = Integer.parseInt(privacieArr[1]);
            int dayPrivacie = Integer.parseInt(privacieArr[2]);

            monthPrivacie = monthPrivacie + termDic.get(type);
            dayPrivacie = dayPrivacie - 1;

            if (dayPrivacie == 0) {
                monthPrivacie -= 1;
                dayPrivacie += 28;
            }

            if (monthPrivacie > 12) {
                yearPrivacie += (monthPrivacie / 12);
                monthPrivacie -= 12 * (monthPrivacie / 12);
            }

            if (monthPrivacie == 0) {
                yearPrivacie -= 1;
                monthPrivacie = 12;
            }

            int privacieInt = yearPrivacie * 10000 + monthPrivacie * 100 + dayPrivacie;

            System.out.println("todayInt = " + todayInt);
            System.out.println("privacieInt = " + privacieInt);
            if (privacieInt < todayInt) {
                anwerList.add(i);

                System.out.println(i);
            }

            System.out.println();
        }
        int[] answer = new int[anwerList.size()];

        for (int j = 0; j < anwerList.size(); j++) {
            answer[j] = anwerList.get(j) + 1;
        }
        System.out.println("Arrays.toString(answer) = " + Arrays.toString(answer));
        return answer;
    }
}