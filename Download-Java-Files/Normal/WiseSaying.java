package com.msproject.myhome.mydays;

import java.util.ArrayList;

class WiseSaying {
    private static final WiseSaying ourInstance = new WiseSaying();
    ArrayList<Saying> says;
    static WiseSaying getInstance() {
        return ourInstance;
    }

    private WiseSaying() {
        says = new ArrayList<>();
        says.add(new Saying("큰 목표를 이루고 싶으면 허락을 구하지 마라.", "미상"));
        says.add(new Saying("상황을 가장 잘 활용하는 사람이 가장 좋은 상황을 맞는다.", "존 우든"));
        says.add(new Saying("늘 하던 대로 하면 늘 얻던 것을 얻는다.", "미상"));
        says.add(new Saying("열정을 잃지 않고 실패에서 실패로 걸어가는 것이 성공이다.", "윈스턴 처칠"));
        says.add(new Saying("애벌레가 세상이 끝났다고 생각하는 순간 나비로 변했다.", "속담 중"));
        says.add(new Saying("기회는 일어나는 것이 아니라 만들어내는 것이다.", "크리스 그로서"));
        says.add(new Saying("지옥을 겪고 있다면 계속 겪어 나가라.", "윈스턴 처칠"));
        says.add(new Saying("언성을 높이지 말고 논거를 강화하라.", "미상"));
        says.add(new Saying("괴로운 시련처럼 보이는 것이 뜻밖의 좋은 일일 때가 많다.", "오스카 와일드"));
        says.add(new Saying("광기와 천재성 사이의 거리는 성공으로만 측정된다.", "브루스 페어스타인"));
        says.add(new Saying("게으른 예술가가 만든 명작은 없다.", "미상"));
        says.add(new Saying("간단하게 설명할 수 없으면 제대로 이해하지 못하는 것이다.", "알버트 아인슈타인"));
        says.add(new Saying("매일 당신을 두렵게 만드는 일을 하나씩 하라.", "미상"));
        says.add(new Saying("인생이란 자신을 찾는 것이 아니라 자신을 만드는 것이다.", "롤리 다스칼"));
        says.add(new Saying("당신의 문제가 문제가 아니라 당신의 반응이 문제다.", "미상"));
        says.add(new Saying("뭐든 할 수 있지만 모든 걸 할 수 있는 건 아니다. ", "미상"));
        says.add(new Saying("혁신이 지도자와 추종자를 가른다.", "스티브 잡스"));
        says.add(new Saying("나는 내가 더 노력할수록 운이 더 좋아진다는 걸 발견했다.", "토마스 제퍼슨"));
        says.add(new Saying("모든 성취의 시작점은 갈망이다.", "나폴레온 힐"));
        says.add(new Saying("성공은 매일 반복한 작은 노력들의 합이다.", "로버트 콜리어"));
        says.add(new Saying("생각하라, 그러면 당신은 부자가 되리라.","나폰레온 힐"));
        says.add(new Saying("모든 진보는 익숙한 영역이 아닌 곳에서 이뤄진다.", "마이클 존 보박"));
        says.add(new Saying("마치지 않고 죽어도 되는 일만 내일로 미뤄라.", "파블로 피카소"));
    }

    public Saying getWiseSay(){
        int index = (int)(Math.random() * (says.size()-1));
        return says.get(index);
    }
}
