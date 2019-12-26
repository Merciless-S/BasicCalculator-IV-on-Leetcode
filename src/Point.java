
class Point{
    int sign, state;
    Poly ans, pre;

    public Point(int sign, Poly ans, Poly pre, int state){
        this.state = state;
        this.sign = sign;
        this.ans = ans;
        this.pre = pre;
    }
}