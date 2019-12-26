import java.util.*;

class Solution {
    HashSet<Character> operator;
    HashMap<String, Integer> variables;
    public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
        this.variables = new HashMap<>();
        for(int i = 0; i < evalvars.length; i++){
            variables.put(evalvars[i], evalints[i]);
        }
        this.operator = new HashSet<>();
        operator.add('+');
        operator.add('-');
        operator.add('*');
        operator.add(')');
        operator.add('(');
        return eval(expression).toStrings();
    }


    private Poly eval(String s){
        Stack<Point> stack = new Stack<>();
        Poly cur = new Poly(), pre = new Poly(), ans = new Poly();
        int sign = 1;
        int state = 0;
        StringBuilder builder = new StringBuilder();
        s += "+";
        for(int i = 0; i < s.length(); ++i){
            char c = s.charAt(i);
            if(c != ' '){
                if(!operator.contains(c)){
                    builder.append(c);
                }else if(c == '('){
                    stack.push(new Point(sign, ans, pre, state));
                    ans = new Poly();
                    state = 0;
                    sign = 1;
                    pre = new Poly();
                }else{
                    if(builder.length() > 0){
                        cur = new Poly();
                        if(isInteger(builder.toString())){

                            cur.addTerm(new ArrayList(), Integer.parseInt(builder.toString()));
                        }else if(this.variables.containsKey(builder.toString())){
                            cur.addTerm(new ArrayList(), variables.get(builder.toString()));
                        }else{
                            List<String> single = new ArrayList<>();
                            single.add(builder.toString());
                            cur.addTerm(single, 1);
                        }
                        builder = new StringBuilder();
                    }
                    if(state == 1)
                        pre.times(cur);
                    else
                        pre = cur;
                    if(c == ')'){
                        Poly doit = new Poly();
                        doit.addTerm(new ArrayList(), sign);
                        pre.times(doit);
                        ans.add(pre);
                        cur = ans;
                        Point temp = stack.pop();
                        sign = temp.sign;
                        ans = temp.ans;
                        pre = temp.pre;
                        state = temp.state;
                    }else if(c == '-' || c == '+'){
                        Poly doit = new Poly();
                        doit.addTerm(new ArrayList(), sign);
                        pre.times(doit);
                        ans.add(pre);
                        state = 0;
                        pre = new Poly();
                        sign = c == '+' ? 1 : -1;
                    }else{
                        state = c == '*'? 1 : 2;
                    }
                }
            }
        }
        return ans;
    }

    private boolean isInteger(String s){
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
}



