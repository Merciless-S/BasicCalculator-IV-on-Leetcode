import java.util.*;

class Poly{
    HashMap<List<String>, Integer> memo;

    public Poly(){
        this.memo = new HashMap<>();
    }

    public void add(Poly that){
        for(Map.Entry<List<String>, Integer> pair: that.memo.entrySet()){
            this.memo.put(pair.getKey(), pair.getValue() + this.memo.getOrDefault(pair.getKey(),0));
        }
    }

    public void addTerm(List<String> s, int num){
        this.memo.put(s, num + this.memo.getOrDefault(s,0));
    }

    public void times(Poly that){
        HashMap<List<String>, Integer> temp = new HashMap<>();
        for(Map.Entry<List<String>, Integer> p1: this.memo.entrySet()){
            for(Map.Entry<List<String>, Integer> p2: that.memo.entrySet()){
                int coe = p1.getValue() * p2.getValue();
                if(coe != 0){
                    List<String> doit = merge(p1.getKey(), p2.getKey());
                    temp.put(doit, coe + temp.getOrDefault(doit, 0));
                }
            }
        }
        this.memo = temp;
    }

    public List<String> merge(List<String> a, List<String> b){
        List<String> res = new ArrayList<>();
        int ai = 0, bi = 0;
        while(ai < a.size() && bi < b.size()){
            if(a.get(ai).compareTo(b.get(bi)) < 0){
                res.add(a.get(ai++));
            }else{
                res.add(b.get(bi++));
            }
        }
        while(ai < a.size() || bi < b.size()){
            if(ai < a.size()){
                res.add(a.get(ai++));
            }else{
                res.add(b.get(bi++));
            }
        }
        return res;
    }

    int compareList(List<String> A, List<String> B) {
        int i = 0;
        for (String x: A) {
            String y = B.get(i++);
            if (x.compareTo(y) != 0) return x.compareTo(y);
        }
        return 0;
    }

    public List<String> toStrings(){
        List<List<String>> keys = new ArrayList(this.memo.keySet());
        Collections.sort(keys, (a, b) ->
                a.size() != b.size() ? b.size() - a.size() : compareList(a, b));
        List<String> res = new ArrayList<String>();
        for(List<String> cur: keys){
            if(this.memo.get(cur) != 0) {
                String s = String.join("*", cur);
                res.add(this.memo.get(cur) + (s.length() > 0 ? "*" + s : ""));
            }
        }
        return res;
    }
}