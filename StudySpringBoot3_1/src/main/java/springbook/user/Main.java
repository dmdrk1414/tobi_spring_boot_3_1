package springbook.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Solution solution = new Solution();
        String[] friends = new String[]{"muzi", "ryan", "frodo", "neo"};
        String[] gifts = new String[]{
                "muzi frodo",
                "muzi frodo",
                "ryan muzi",
                "ryan muzi",
                "ryan muzi",
                "frodo muzi",
                "frodo ryan",
                "neo muzi"};
        solution.solution(friends, gifts);
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
        int sizeOfFriends = friends.length;
        Map<String, Integer> nameDic = new HashMap<>();
        int[] giftState = new int[sizeOfFriends];
        int[][] giveAndTakeGraph = new int[sizeOfFriends][sizeOfFriends];

        // nameDic 설정
        for (int i = 0; i < sizeOfFriends; i++) {
            String friend = friends[i];
            nameDic.put(friend, i);
        }

        // giftState 설정
        for (String gift : gifts) {
            String[] giftSplit = gift.split(" ");
            String give = giftSplit[0];
            String take = giftSplit[1];

            Integer giveFriendIndex = nameDic.get(give);
            Integer takeFriendIndex = nameDic.get(take);
            giftState[giveFriendIndex]++;
            giftState[takeFriendIndex]--;
            giveAndTakeGraph[giveFriendIndex][takeFriendIndex]++;
        }

        for (int giveIndex = 0; giveIndex < sizeOfFriends; giveIndex++) {
            int num = 0;
            for (int takeIndex = 0; takeIndex < sizeOfFriends; takeIndex++) {

                if (giveIndex == takeIndex) {
                    continue;
                }

                if (giveAndTakeGraph[giveIndex][takeIndex] > giveAndTakeGraph[takeIndex][giveIndex]) {
                    num++;
                }

                if (giveAndTakeGraph[giveIndex][takeIndex] == giveAndTakeGraph[takeIndex][giveIndex] &&
                        giftState[giveIndex] > giftState[takeIndex]
                ) {
                    num++;
                }

                if (answer < num) {
                    answer = num;
                }
            }
        }

        return answer;
    }
}