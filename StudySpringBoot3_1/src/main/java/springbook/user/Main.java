package springbook.user;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Solution solution = new Solution();
        String[] frends = new String[]{"muzi", "ryan", "frodo", "neo"};
        String[] gifts = new String[]{
                "muzi frodo",
                "muzi frodo",
                "ryan muzi",
                "ryan muzi",
                "ryan muzi",
                "frodo muzi",
                "frodo ryan",
                "neo muzi"};
        solution.solution(frends, gifts);
    }
}

/*
* friends 친구들의 이름을 담은 1차원 문자열 배열
 	(2 ≤ friends의 길이 = 친구들의 수 ≤ 50)
 	friends의 원소는 친구의 이름을 의미하는 알파벳 소문자로 이루어진 길이가 10 이하인 문자열입니다.
 	이름이 같은 친구는 없습니다.
* gifts 이번 달까지 친구들이 주고받은 선물 기록을 담은 1차원 문자열 배열
	1 ≤ gifts의 길이 ≤ 10,000
    gifts의 원소는 "A B"형태의 문자열입니다.
    A는 선물을 준 친구의 이름을 B는 선물을 받은 친구의 이름을 의미하며 공백 하나로 구분됩니다.
    A와 B는 friends의 원소이며 A와 B가 같은 이름인 경우는 존재하지 않습니다.
* gifts_len 배열 gifts의 길이입니다.
* return 다음달에 가장 많은 선물을 받는 친구가 받을 선물의 수
*/
class Solution {
    public int solution(String[] friends, String[] gifts) {
        int answer = 0;
        Integer friendLen = friends.length;
        Map<String, Integer> dic = new HashMap<>(); // 친구들의 이름은 index으로 구분
        int[] giftDegree = new int[friendLen];
        int[][] giftGraph = new int[friendLen][friendLen];

        for (int i = 0; i < friendLen; i++) {
            dic.put(friends[i], i);
        }

        for (String gift : gifts) {
            String[] giftName = gift.split(" ");
            giftDegree[dic.get(giftName[0])]++;
            giftDegree[dic.get(giftName[1])]--;
            giftGraph[dic.get(giftName[0])][dic.get(giftName[1])]++;
        }
        for (int i = 0; i < friendLen; i++) {
            for (int j = 0; j < friendLen; j++) {
                System.out.print(giftGraph[i][j] + " ");
            }
            System.out.println();
        }

        // 1. if A가 B에게 선물을 5번, B가 A에게 선물 3번이면
        // 1.1  B가 A한테 선물 하나준다.

        // 2. 선물을 주고받은 기록이 없거나, 같다면
        // 2.1  선물 지수가 작은 사람이 -> 큰사람 한테
        // 2.2  선물 지수가 같다면 -> 다음달 선물이 없다.
        // 선물 지수 : 친구들에게 준 선물의 수 - 선물을 받은 수
        // ex) A가 준 선물이 3개 - 받은 선물 10 = -7의 지수이다.
        // 	   B가 준 선물이 3개 - 받은 선물 2 = 1이다.
        //	   A -> B에게 선물
        for (int i = 0; i < friendLen; i++) {
            int num = 0;
            for (int j = 0; j < friendLen; j++) {
                if (i == j) {
                    continue;
                }

                if (giftGraph[i][j] > giftGraph[j][i]) {
                    num++;
                }

                if (giftGraph[i][j] == giftGraph[j][i] && giftDegree[i] > giftDegree[j]) {
                    num++;
                }

            }

            if (answer < num) {
                answer = num;
            }
        }

        return answer;
    }

    public Boolean isContain(Map<String, Integer> map, String target) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String item = entry.getKey();
            if (item.contains(target)) {
                return true;
            }
        }
        return false;
    }

    public void println(Object object) {
        System.out.println(object);
    }
}