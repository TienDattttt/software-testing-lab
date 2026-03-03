/* Mo hinh bounded buffer + semaphore (gioi han 5 lan), co LTL
   - N = 5 o dem
   - Producer san xuat toi da 5 lan
   - Consumer tieu thu toi da 5 lan
*/
#define N 5
#define K 5   /* so lan produce/consume */
byte buffer[N];
byte top = 0;
/* semaphore */
byte notFull  = N;
byte notEmpty = 0;
byte s = 1;
/* dem so lan */
byte pcount = 0;
byte ccount = 0;
/* verification-only */
bool in_cs[2];
bool cons_evt = 0;
inline wait(sem)   { atomic { (sem > 0) -> sem-- } }
inline signal(sem) { atomic { sem++ } }
active proctype Producer() {
  byte d = 0;
  do
  :: (pcount < K) ->
       d++;
       wait(notFull);
       wait(s);
         in_cs[_pid] = 1;
         buffer[top] = d;
         top++;
         in_cs[_pid] = 0;
       signal(s);
       signal(notEmpty);
       pcount++
  :: else -> break
  od
}
active proctype Consumer() {
  byte d;
  do
  :: (ccount < K) ->
       wait(notEmpty);
       wait(s);
         in_cs[_pid] = 1;
         top--;
         d = buffer[top];
         /* danh dau su kien consume */
         cons_evt = 1; cons_evt = 0;
         in_cs[_pid] = 0;
       signal(s);
       signal(notFull);
       ccount++
  :: else -> break
  od
}
/* LTL (safety) */
ltl mutual_exclusion { [] !(in_cs[0] && in_cs[1]) }
ltl bounded_top      { [] (top <= N) }
/* "liveness" dang bounded: cuoi cung consumer se consume du K lan */
