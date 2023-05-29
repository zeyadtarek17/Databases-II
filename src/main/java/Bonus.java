// package main.java;

// import java.util.Hashtable;
// import java.util.Stack;
// import java.util.Vector;

// public class Bonus {
//     Stack<Object> obj ;
//     Stack<Operation> operations ;
//     Table t;
//     public Bonus(Table t){
//         this.t = t;
//         obj = new Stack<>();
//         operations = new Stack<>();
//     }
    
//     public class Operation {
//         String op;
//         int priority;

//         public Operation(String o) throws DBAppException {
//             op = o;
//             switch (o) {
//                 case ("XOR"):
//                     priority = 1;
//                     break;
//                 case ("OR"):
//                     priority = 2;
//                     break;
//                 case ("AND"):
//                     priority = 3;
//                     break;
//                 default:
//                     throw new DBAppException("Star operator must be one of AND, OR, XOR!");
//             }
//         }
        
//         public String toString() {
//             return op;
//         }
    
//     }

 
//     public Vector<Hashtable> sqlSolve(SQLTerm[] arrSQLTerms, String[] strarrOperators) throws DBAppException{
//         obj.push(arrSQLTerms[0]);
//         obj.push(arrSQLTerms[1]);

//         operations.push(new Operation(strarrOperators[0]));

//         for(int i=0; i<strarrOperators.length-1;i++){
//             SQLTerm sqloop = arrSQLTerms[i+2]; 
//             Operation op = new Operation(strarrOperators[i+1]);
//             Operation top = operations.peek();
//             if(op.priority <= top.priority){
//                 Object o2 = obj.pop();
//                 Object o1 = obj.pop();
//                 Operation operation = operations.pop();
//                 obj.push(applyOperation(o1,o2,operation.op));
//             }
//             obj.push(sqloop);
//             operations.push(op);
            
//             while(!operations.isEmpty() && op.priority <= operations.peek().priority){
//                 Object o2 = obj.pop();
//                 Object o1 = obj.pop();
//                 Operation operation = operations.pop();
//                 obj.push(applyOperation(o1,o2,operation.op));
//             }
//             obj.push(arrSQLTerms[i+2]);
//             operations.push(op);
//         }
//         while(obj.size()>1){
//             Object o2 = obj.pop();
//             Object o1 = obj.pop();
//             Operation operation = operations.pop();
//             obj.push(applyOperation(o1,o2,operation.op));
//         }
//         return (Vector<Hashtable>) obj.pop();

//     }

//     public Vector<Hashtable> applyOperation(Object ob1, Object ob2, String operation) throws DBAppException{
//         switch(operation){
//             case "AND":
                
//                 return ANDOp((Vector<Hashtable>) ob1, (Vector<Hashtable>) ob2);

//             case "OR":
//             if(isInstance(ob1)){
//                 ob1 = t.solveStatement((SQLTerm) ob1);
//             }
//             if(isInstance(ob2)){
//                 ob2 = t.solveStatement((SQLTerm) ob2);
//             }
//                 return OROp((Vector<Hashtable>) ob1, (Vector<Hashtable>) ob2);


//             case "XOR":
//             if(isInstance(ob1)){
//                 ob1= t.solveStatement((SQLTerm) ob1);
//             }
//             if(isInstance(ob2)){
//                 ob2 = t.solveStatement((SQLTerm) ob2);
//             }
//                 return XOROp((Vector<Hashtable>) ob1, (Vector<Hashtable>) ob2);

//             default:
//                 throw new DBAppException("Star operator must be one of AND, OR, XOR!");
//         }

//     }
//     public Vector<Hashtable> XOROp(Vector<Hashtable> v1, Vector<Hashtable> v2){
//         Vector<Hashtable> result = new Vector<>();
//         for(int i=0; i<v1.size(); i++){
//             Hashtable h = (Hashtable) v1.get(i);
//             if(!v2.contains(h)){
//                 result.add(h);
//             }
//         }
//         for(int i=0; i<v2.size(); i++){
//             Hashtable h = (Hashtable) v2.get(i);
//             if(!v1.contains(h)){
//                 result.add(h);
//             }
//         }
//         return result;
//     }

//     public static Vector<Hashtable> OROp(Vector<Hashtable> v1, Vector<Hashtable> v2){
//         Vector<Hashtable> result = new Vector<>();
//         for(int i=0; i<v1.size(); i++){
//             Hashtable h = (Hashtable) v1.get(i);
//             result.add(h);
//         }
//         for(int i=0; i<v2.size(); i++){
//             Hashtable h = (Hashtable) v2.get(i);
//             if(!result.contains(h)){
//                 result.add(h);
//             }
//         }
//         return result;
//     }

//     public Vector<Hashtable> ANDOp(Object ob1, Object ob2) throws DBAppException{
//         if(isInstance(ob1) && isInstance(ob2)){
//             return AndHelper((SQLTerm) ob1, (SQLTerm) ob2);
//         }
//         else{
//             SQLTerm sql;
//             Vector vector;
//             if(isInstance(ob1) && ob2 instanceof Vector){
//                 sql = (SQLTerm) ob1;
//                 vector = (Vector) ob2;
//                 return AndHelper(sql, vector);
//             }
//             else if(isInstance(ob2) && ob1 instanceof Vector){
//                 sql = (SQLTerm) ob2;
//                 vector = (Vector) ob1;
//                 return AndHelper(sql, vector);
//             }
//             else{
//                 return AndHelper((Vector<Hashtable>) ob1, (Vector<Hashtable>) ob2);
//             }
//         }

//     }
    

//     public Vector<Hashtable> AndHelper(SQLTerm sql1, SQLTerm sql2) throws DBAppException{
//         Vector<String> result = new Vector<String>();
//         result.add(sql1._strColumnName);
//         result.add(sql2._strColumnName);
//         boolean flag = sql1._strTableName.equals(t.strTableName);
//         boolean flag2 = sql2._strTableName.equals(t.strTableName);
//         if (!flag || !flag2) {
//             Vector<String> temp = new Vector<String>();
//             temp.add(sql1._strTableName);     //unhandled case
//             return null;        //not done    
//         }
//         else{
//             Vector<Hashtable> v1 = t.Traverse(sql1);
//             Vector<Hashtable> v2 = t.Traverse(sql2);
//             return AndHelper(v1, v2);
//         }


      
//     }
//     public Vector<Hashtable> AndHelper(Vector<Hashtable> v1, Vector<Hashtable> v2){
//         Vector<Hashtable> result = new Vector<>();
//         for(int i=0; i<v1.size(); i++){
//             Hashtable h = (Hashtable) v1.get(i);
//             if(v2.contains(h)){
//                 result.add(h);
//             }
//         }
//         return result;
//     }

//     public Vector<Hashtable> AndHelper(SQLTerm sql, Vector<Hashtable> vector){
//         Vector<Hashtable> result = new Vector<>();
//         for(int i=0; i<vector.size(); i++){
//             Hashtable h = (Hashtable) vector.get(i);
//             if(checkCondition(h, sql)){
//                 result.add(h);
//             }
//         }
//         return result;
//     }

//     public static boolean checkCondition(Hashtable h, SQLTerm sql){
//         String colName = sql._strColumnName;
//         String colType = sql._strOperator;
//         String colValue = sql._objValue.toString();
//         String hValue = h.get(colName).toString();
//         switch(colType){
//             case ">":
//                 return hValue.compareTo(colValue) > 0;
//             case ">=":
//                 return hValue.compareTo(colValue) >= 0;
//             case "<":
//                 return hValue.compareTo(colValue) < 0;
//             case "<=":
//                 return hValue.compareTo(colValue) <= 0;
//             case "=":
//                 return hValue.compareTo(colValue) == 0;
//             case "!=":
//                 return hValue.compareTo(colValue) != 0;
//             default:
//                 return false;
//         }
//     }


//     public boolean isInstance(Object o){
//         //return o instanceof SQLTerm;
//     }
// }
