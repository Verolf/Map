package main.java;

import main.java.weapon.*;

import java.util.concurrent.TimeUnit;

class Solder {
    private Integer x;
    private Integer y;
    private Integer hp; //private
    private final Integer fullHp; //private
    private Up7 field;
    private Integer team;
    private Weapon gun;
    private double skill;
    private boolean dead;
    public boolean running;

    Solder(Up7 field, int x, int y, int team) {
        super();
        this.field = field;
        this.x = x;
        this.y = y;
        this.team = team;
        this.hp = 80 + (int)(Math.random()*40);
        this.fullHp = this.hp;
        this.dead = false;
        this.skill = 1.0;
        int i = (int)(Math.random()*4);
        switch (i) {
            case 0:
                this.gun = new Pistol();
                break;
            case 1:
                this.gun = new Shothun();
                break;
            case 2:
                this.gun = new AssaultRifle();
                break;
            case 3:
                this.gun = new Rifle();
        }
        this.running = false;
        field.repaint();
    }



    void move(int x, int y) throws InterruptedException {
        this.running = true;
        this.x = x;
        this.y = y;
        field.repaint();
        TimeUnit.MILLISECONDS.sleep(300);
        this.running = false;
    }

    int getTeam() {
        return team;
    }

    int getHp() {
        return hp;
    }

    double percentOfHp() {
        return (double)this.hp/this.fullHp;
    }

    boolean capturedOfFlag() {
        if ((Math.pow((this.x - 400), 2) + Math.pow((this.y - 400), 2)) < 400) {
            return true;
        } else {
            return false;
        }
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public void isDead(){
        if (hp < 0) {
            this.dead = true;
            Up7.deadSolders.add(this);
            Up7.solders.remove(this);
        }
    }

    public void hitting(Solder enemySolder){//у солдата с индексом b отнимается a хп
        enemySolder.hp = enemySolder.hp - this.gun.getDamage();
        enemySolder.isDead();
    }

    public void raiseSkill(){
        this.skill = this.skill + 1.0;
    }

    public Solder findTarget(){ //ищет цель
        int i;
        double k; //k - расстояние от выбранного солдата до солдатов в массиве
        for (i = 0; i < Up7.solders.size(); i++) {
            k = (Math.sqrt(Math.pow(Up7.solders.get(i).getX() - this.getX(), 2) + Math.pow(Up7.solders.get(i).getY() - this.getY(), 2)));
            if ((k < (double)this.gun.getDistance()) & !(Up7.solders.get(i).equals(this))){
                return Up7.solders.get(i); //возвращает ссылку первого солдата попавшего в дистанцию стрельбы
            }
        }
        return null; //ошибка если не нашлось цели
    }

    public void shoot(Solder enemySolder) throws InterruptedException {//стреляет из а в b
        this.running = true;
        double v;
        v = Math.atan(skill/4) * 2 / 3.141592654;
        if (Math.random() < v) {
            this.hitting(enemySolder);
        }
        this.raiseSkill(); //после выстрела у стрелка поднялся скил.

        field.repaint();
        TimeUnit.MILLISECONDS.sleep(300);
        this.running = false;
    }

}



