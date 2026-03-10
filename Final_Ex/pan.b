	switch (t->back) {
	default: Uerror("bad return move");
	case  0: goto R999; /* nothing to undo */

		 /* CLAIM producer_no_overwrite */
;
		
	case 3: // STATE 1
		goto R999;

	case 4: // STATE 10
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* CLAIM non_starvation */
;
		;
		;
		;
		
	case 7: // STATE 13
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* CLAIM mutual_exclusion */
;
		
	case 8: // STATE 1
		goto R999;

	case 9: // STATE 10
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* PROC Consumer */
;
		;
		
	case 11: // STATE 3
		;
		now.lock = trpt->bup.oval;
		;
		goto R999;

	case 12: // STATE 6
		;
		now.in_cs[ Index(((P1 *)_this)->_pid, 2) ] = trpt->bup.oval;
		;
		goto R999;

	case 13: // STATE 7
		;
		cons_pre_flag = trpt->bup.oval;
		;
		goto R999;

	case 14: // STATE 8
		;
		now.flag = trpt->bup.oval;
		;
		goto R999;

	case 15: // STATE 9
		;
		now.in_cs[ Index(((P1 *)_this)->_pid, 2) ] = trpt->bup.oval;
		;
		goto R999;

	case 16: // STATE 10
		;
		now.cons_evt = trpt->bup.oval;
		;
		goto R999;

	case 17: // STATE 11
		;
		now.cons_evt = trpt->bup.oval;
		;
		goto R999;

	case 18: // STATE 12
		;
		now.lock = trpt->bup.oval;
		;
		goto R999;

	case 19: // STATE 18
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* PROC Producer */
;
		;
		
	case 21: // STATE 3
		;
		now.lock = trpt->bup.oval;
		;
		goto R999;

	case 22: // STATE 6
		;
		now.in_cs[ Index(((P0 *)_this)->_pid, 2) ] = trpt->bup.oval;
		;
		goto R999;

	case 23: // STATE 7
		;
		now.prod_pre_flag = trpt->bup.oval;
		;
		goto R999;

	case 24: // STATE 8
		;
		now.flag = trpt->bup.oval;
		;
		goto R999;

	case 25: // STATE 9
		;
		now.in_cs[ Index(((P0 *)_this)->_pid, 2) ] = trpt->bup.oval;
		;
		goto R999;

	case 26: // STATE 10
		;
		now.prod_evt = trpt->bup.oval;
		;
		goto R999;

	case 27: // STATE 11
		;
		now.prod_evt = trpt->bup.oval;
		;
		goto R999;

	case 28: // STATE 12
		;
		now.lock = trpt->bup.oval;
		;
		goto R999;

	case 29: // STATE 18
		;
		p_restor(II);
		;
		;
		goto R999;
	}

