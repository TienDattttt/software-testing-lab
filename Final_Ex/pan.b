	switch (t->back) {
	default: Uerror("bad return move");
	case  0: goto R999; /* nothing to undo */

		 /* CLAIM bounded_top */
;
		
	case 3: // STATE 1
		goto R999;

	case 4: // STATE 10
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* CLAIM mutual_exclusion */
;
		
	case 5: // STATE 1
		goto R999;

	case 6: // STATE 10
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* PROC Consumer */
;
		;
		
	case 8: // STATE 3
		;
		now.notEmpty = trpt->bup.oval;
		;
		goto R999;

	case 9: // STATE 7
		;
		now.s = trpt->bup.oval;
		;
		goto R999;

	case 10: // STATE 10
		;
		now.in_cs[ Index(((P1 *)_this)->_pid, 2) ] = trpt->bup.oval;
		;
		goto R999;

	case 11: // STATE 11
		;
		now.top = trpt->bup.oval;
		;
		goto R999;

	case 12: // STATE 12
		;
		((P1 *)_this)->d = trpt->bup.ovals[0];
		;
		ungrab_ints(trpt->bup.ovals, 2);
		goto R999;

	case 13: // STATE 13
		;
		cons_evt = trpt->bup.oval;
		;
		goto R999;

	case 14: // STATE 14
		;
		cons_evt = trpt->bup.oval;
		;
		goto R999;

	case 15: // STATE 15
		;
		now.in_cs[ Index(((P1 *)_this)->_pid, 2) ] = trpt->bup.oval;
		;
		goto R999;

	case 16: // STATE 16
		;
		now.s = trpt->bup.oval;
		;
		goto R999;

	case 17: // STATE 19
		;
		now.notFull = trpt->bup.oval;
		;
		goto R999;

	case 18: // STATE 22
		;
		now.ccount = trpt->bup.oval;
		;
		goto R999;

	case 19: // STATE 28
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* PROC Producer */
;
		;
		
	case 21: // STATE 2
		;
		((P0 *)_this)->d = trpt->bup.oval;
		;
		goto R999;

	case 22: // STATE 4
		;
		now.notFull = trpt->bup.oval;
		;
		goto R999;

	case 23: // STATE 8
		;
		now.s = trpt->bup.oval;
		;
		goto R999;

	case 24: // STATE 11
		;
		now.in_cs[ Index(((P0 *)_this)->_pid, 2) ] = trpt->bup.oval;
		;
		goto R999;

	case 25: // STATE 12
		;
		now.buffer[ Index(now.top, 5) ] = trpt->bup.oval;
		;
		goto R999;

	case 26: // STATE 13
		;
		now.top = trpt->bup.oval;
		;
		goto R999;

	case 27: // STATE 14
		;
		now.in_cs[ Index(((P0 *)_this)->_pid, 2) ] = trpt->bup.oval;
		;
		goto R999;

	case 28: // STATE 15
		;
		now.s = trpt->bup.oval;
		;
		goto R999;

	case 29: // STATE 18
		;
		now.notEmpty = trpt->bup.oval;
		;
		goto R999;

	case 30: // STATE 21
		;
		now.pcount = trpt->bup.oval;
		;
		goto R999;

	case 31: // STATE 27
		;
		p_restor(II);
		;
		;
		goto R999;
	}

