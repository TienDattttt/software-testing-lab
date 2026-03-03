/* Mo hinh Producer-Consumer voi 1 flag bit + mutex
   Verify LTL:
   (1) mutual exclusion (safety)
   (2) non-starvation (liveness)
   (3) producer-consumer discipline (safety)
*/
bool flag = 0;
/* mutex de dam bao mutual exclusion */
bool lock = 0;
/* verification-only */
bool in_cs[2];
bool prod_evt = 0;
bool cons_evt = 0;
bool prod_pre_flag = 0;
bool cons_pre_flag = 0;
inline acquire() {
  /* doi den khi lock mo, roi chiem lock */
  atomic { (lock == 0) -> lock = 1; }
}
inline release() {
  atomic { lock = 0; }
}
active proctype Producer() {
  do
  :: (flag == 0) ->
       acquire();
       in_cs[_pid] = 1;
       prod_pre_flag = flag;
       /* ghi */
       flag = 1;
       in_cs[_pid] = 0;
       prod_evt = 1;
       prod_evt = 0;
       release()
  od
}
active proctype Consumer() {
  do
  :: (flag == 1) ->
       acquire();
       in_cs[_pid] = 1;
       cons_pre_flag = flag;
       /* doc */
       flag = 0;
       in_cs[_pid] = 0;
       cons_evt = 1;
       cons_evt = 0;
       release()
  od
}
/* LTL */
ltl mutual_exclusion { [] !(in_cs[0] && in_cs[1]) }
ltl non_starvation   { [] <> cons_evt }
ltl producer_no_overwrite   { [] (prod_evt -> (prod_pre_flag == 0)) }
