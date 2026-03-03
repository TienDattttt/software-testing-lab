#define rand	pan_rand
#define pthread_equal(a,b)	((a)==(b))
#if defined(HAS_CODE) && defined(VERBOSE)
	#ifdef BFS_PAR
		bfs_printf("Pr: %d Tr: %d\n", II, t->forw);
	#else
		cpu_printf("Pr: %d Tr: %d\n", II, t->forw);
	#endif
#endif
	switch (t->forw) {
	default: Uerror("bad forward move");
	case 0:	/* if without executable clauses */
		continue;
	case 1: /* generic 'goto' or 'skip' */
		IfNotBlocked
		_m = 3; goto P999;
	case 2: /* generic 'else' */
		IfNotBlocked
		if (trpt->o_pm&1) continue;
		_m = 3; goto P999;

		 /* CLAIM bounded_top */
	case 3: // STATE 1 - _spin_nvr.tmp:12 - [(!((top<=5)))] (6:0:0 - 1)
		
#if defined(VERI) && !defined(NP)
#if NCLAIMS>1
		{	static int reported1 = 0;
			if (verbose && !reported1)
			{	int nn = (int) ((Pclaim *)pptr(0))->_n;
				printf("depth %ld: Claim %s (%d), state %d (line %d)\n",
					depth, procname[spin_c_typ[nn]], nn, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported1 = 1;
				fflush(stdout);
		}	}
#else
		{	static int reported1 = 0;
			if (verbose && !reported1)
			{	printf("depth %d: Claim, state %d (line %d)\n",
					(int) depth, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported1 = 1;
				fflush(stdout);
		}	}
#endif
#endif
		reached[3][1] = 1;
		if (!( !((((int)now.top)<=5))))
			continue;
		/* merge: assert(!(!((top<=5))))(0, 2, 6) */
		reached[3][2] = 1;
		spin_assert( !( !((((int)now.top)<=5))), " !( !((top<=5)))", II, tt, t);
		/* merge: .(goto)(0, 7, 6) */
		reached[3][7] = 1;
		;
		_m = 3; goto P999; /* 2 */
	case 4: // STATE 10 - _spin_nvr.tmp:17 - [-end-] (0:0:0 - 1)
		
#if defined(VERI) && !defined(NP)
#if NCLAIMS>1
		{	static int reported10 = 0;
			if (verbose && !reported10)
			{	int nn = (int) ((Pclaim *)pptr(0))->_n;
				printf("depth %ld: Claim %s (%d), state %d (line %d)\n",
					depth, procname[spin_c_typ[nn]], nn, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported10 = 1;
				fflush(stdout);
		}	}
#else
		{	static int reported10 = 0;
			if (verbose && !reported10)
			{	printf("depth %d: Claim, state %d (line %d)\n",
					(int) depth, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported10 = 1;
				fflush(stdout);
		}	}
#endif
#endif
		reached[3][10] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* CLAIM mutual_exclusion */
	case 5: // STATE 1 - _spin_nvr.tmp:3 - [(!(!((in_cs[0]&&in_cs[1]))))] (6:0:0 - 1)
		
#if defined(VERI) && !defined(NP)
#if NCLAIMS>1
		{	static int reported1 = 0;
			if (verbose && !reported1)
			{	int nn = (int) ((Pclaim *)pptr(0))->_n;
				printf("depth %ld: Claim %s (%d), state %d (line %d)\n",
					depth, procname[spin_c_typ[nn]], nn, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported1 = 1;
				fflush(stdout);
		}	}
#else
		{	static int reported1 = 0;
			if (verbose && !reported1)
			{	printf("depth %d: Claim, state %d (line %d)\n",
					(int) depth, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported1 = 1;
				fflush(stdout);
		}	}
#endif
#endif
		reached[2][1] = 1;
		if (!( !( !((((int)now.in_cs[0])&&((int)now.in_cs[1]))))))
			continue;
		/* merge: assert(!(!(!((in_cs[0]&&in_cs[1])))))(0, 2, 6) */
		reached[2][2] = 1;
		spin_assert( !( !( !((((int)now.in_cs[0])&&((int)now.in_cs[1]))))), " !( !( !((in_cs[0]&&in_cs[1]))))", II, tt, t);
		/* merge: .(goto)(0, 7, 6) */
		reached[2][7] = 1;
		;
		_m = 3; goto P999; /* 2 */
	case 6: // STATE 10 - _spin_nvr.tmp:8 - [-end-] (0:0:0 - 1)
		
#if defined(VERI) && !defined(NP)
#if NCLAIMS>1
		{	static int reported10 = 0;
			if (verbose && !reported10)
			{	int nn = (int) ((Pclaim *)pptr(0))->_n;
				printf("depth %ld: Claim %s (%d), state %d (line %d)\n",
					depth, procname[spin_c_typ[nn]], nn, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported10 = 1;
				fflush(stdout);
		}	}
#else
		{	static int reported10 = 0;
			if (verbose && !reported10)
			{	printf("depth %d: Claim, state %d (line %d)\n",
					(int) depth, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported10 = 1;
				fflush(stdout);
		}	}
#endif
#endif
		reached[2][10] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* PROC Consumer */
	case 7: // STATE 1 - bb.pml:42 - [((ccount<5))] (0:0:0 - 1)
		IfNotBlocked
		reached[1][1] = 1;
		if (!((((int)now.ccount)<5)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 8: // STATE 2 - bb.pml:20 - [((notEmpty>0))] (9:0:1 - 1)
		IfNotBlocked
		reached[1][2] = 1;
		if (!((((int)now.notEmpty)>0)))
			continue;
		/* merge: notEmpty = (notEmpty-1)(0, 3, 9) */
		reached[1][3] = 1;
		(trpt+1)->bup.oval = ((int)now.notEmpty);
		now.notEmpty = (((int)now.notEmpty)-1);
#ifdef VAR_RANGES
		logval("notEmpty", ((int)now.notEmpty));
#endif
		;
		_m = 3; goto P999; /* 1 */
	case 9: // STATE 6 - bb.pml:20 - [((s>0))] (10:0:1 - 1)
		IfNotBlocked
		reached[1][6] = 1;
		if (!((((int)now.s)>0)))
			continue;
		/* merge: s = (s-1)(0, 7, 10) */
		reached[1][7] = 1;
		(trpt+1)->bup.oval = ((int)now.s);
		now.s = (((int)now.s)-1);
#ifdef VAR_RANGES
		logval("s", ((int)now.s));
#endif
		;
		_m = 3; goto P999; /* 1 */
	case 10: // STATE 10 - bb.pml:45 - [in_cs[_pid] = 1] (0:0:1 - 1)
		IfNotBlocked
		reached[1][10] = 1;
		(trpt+1)->bup.oval = ((int)now.in_cs[ Index(((int)((P1 *)_this)->_pid), 2) ]);
		now.in_cs[ Index(((P1 *)_this)->_pid, 2) ] = 1;
#ifdef VAR_RANGES
		logval("in_cs[_pid]", ((int)now.in_cs[ Index(((int)((P1 *)_this)->_pid), 2) ]));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 11: // STATE 11 - bb.pml:46 - [top = (top-1)] (0:0:1 - 1)
		IfNotBlocked
		reached[1][11] = 1;
		(trpt+1)->bup.oval = ((int)now.top);
		now.top = (((int)now.top)-1);
#ifdef VAR_RANGES
		logval("top", ((int)now.top));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 12: // STATE 12 - bb.pml:47 - [d = buffer[top]] (0:0:2 - 1)
		IfNotBlocked
		reached[1][12] = 1;
		(trpt+1)->bup.ovals = grab_ints(2);
		(trpt+1)->bup.ovals[0] = ((int)((P1 *)_this)->d);
		((P1 *)_this)->d = ((int)now.buffer[ Index(((int)now.top), 5) ]);
#ifdef VAR_RANGES
		logval("Consumer:d", ((int)((P1 *)_this)->d));
#endif
		;
		if (TstOnly) return 1; /* TT */
		/* dead 2: d */  
#ifdef HAS_CODE
		if (!readtrail)
#endif
			((P1 *)_this)->d = 0;
		_m = 3; goto P999; /* 0 */
	case 13: // STATE 13 - bb.pml:49 - [cons_evt = 1] (0:0:1 - 1)
		IfNotBlocked
		reached[1][13] = 1;
		(trpt+1)->bup.oval = ((int)cons_evt);
		cons_evt = 1;
#ifdef VAR_RANGES
		logval("cons_evt", ((int)cons_evt));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 14: // STATE 14 - bb.pml:49 - [cons_evt = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[1][14] = 1;
		(trpt+1)->bup.oval = ((int)cons_evt);
		cons_evt = 0;
#ifdef VAR_RANGES
		logval("cons_evt", ((int)cons_evt));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 15: // STATE 15 - bb.pml:50 - [in_cs[_pid] = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[1][15] = 1;
		(trpt+1)->bup.oval = ((int)now.in_cs[ Index(((int)((P1 *)_this)->_pid), 2) ]);
		now.in_cs[ Index(((P1 *)_this)->_pid, 2) ] = 0;
#ifdef VAR_RANGES
		logval("in_cs[_pid]", ((int)now.in_cs[ Index(((int)((P1 *)_this)->_pid), 2) ]));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 16: // STATE 16 - bb.pml:21 - [s = (s+1)] (0:0:1 - 1)
		IfNotBlocked
		reached[1][16] = 1;
		(trpt+1)->bup.oval = ((int)now.s);
		now.s = (((int)now.s)+1);
#ifdef VAR_RANGES
		logval("s", ((int)now.s));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 17: // STATE 19 - bb.pml:21 - [notFull = (notFull+1)] (0:0:1 - 1)
		IfNotBlocked
		reached[1][19] = 1;
		(trpt+1)->bup.oval = ((int)now.notFull);
		now.notFull = (((int)now.notFull)+1);
#ifdef VAR_RANGES
		logval("notFull", ((int)now.notFull));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 18: // STATE 22 - bb.pml:53 - [ccount = (ccount+1)] (0:0:1 - 1)
		IfNotBlocked
		reached[1][22] = 1;
		(trpt+1)->bup.oval = ((int)now.ccount);
		now.ccount = (((int)now.ccount)+1);
#ifdef VAR_RANGES
		logval("ccount", ((int)now.ccount));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 19: // STATE 28 - bb.pml:56 - [-end-] (0:0:0 - 3)
		IfNotBlocked
		reached[1][28] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* PROC Producer */
	case 20: // STATE 1 - bb.pml:25 - [((pcount<5))] (0:0:0 - 1)
		IfNotBlocked
		reached[0][1] = 1;
		if (!((((int)now.pcount)<5)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 21: // STATE 2 - bb.pml:26 - [d = (d+1)] (0:0:1 - 1)
		IfNotBlocked
		reached[0][2] = 1;
		(trpt+1)->bup.oval = ((int)((P0 *)_this)->d);
		((P0 *)_this)->d = (((int)((P0 *)_this)->d)+1);
#ifdef VAR_RANGES
		logval("Producer:d", ((int)((P0 *)_this)->d));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 22: // STATE 3 - bb.pml:20 - [((notFull>0))] (10:0:1 - 1)
		IfNotBlocked
		reached[0][3] = 1;
		if (!((((int)now.notFull)>0)))
			continue;
		/* merge: notFull = (notFull-1)(0, 4, 10) */
		reached[0][4] = 1;
		(trpt+1)->bup.oval = ((int)now.notFull);
		now.notFull = (((int)now.notFull)-1);
#ifdef VAR_RANGES
		logval("notFull", ((int)now.notFull));
#endif
		;
		_m = 3; goto P999; /* 1 */
	case 23: // STATE 7 - bb.pml:20 - [((s>0))] (11:0:1 - 1)
		IfNotBlocked
		reached[0][7] = 1;
		if (!((((int)now.s)>0)))
			continue;
		/* merge: s = (s-1)(0, 8, 11) */
		reached[0][8] = 1;
		(trpt+1)->bup.oval = ((int)now.s);
		now.s = (((int)now.s)-1);
#ifdef VAR_RANGES
		logval("s", ((int)now.s));
#endif
		;
		_m = 3; goto P999; /* 1 */
	case 24: // STATE 11 - bb.pml:29 - [in_cs[_pid] = 1] (0:0:1 - 1)
		IfNotBlocked
		reached[0][11] = 1;
		(trpt+1)->bup.oval = ((int)now.in_cs[ Index(((int)((P0 *)_this)->_pid), 2) ]);
		now.in_cs[ Index(((P0 *)_this)->_pid, 2) ] = 1;
#ifdef VAR_RANGES
		logval("in_cs[_pid]", ((int)now.in_cs[ Index(((int)((P0 *)_this)->_pid), 2) ]));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 25: // STATE 12 - bb.pml:30 - [buffer[top] = d] (0:0:1 - 1)
		IfNotBlocked
		reached[0][12] = 1;
		(trpt+1)->bup.oval = ((int)now.buffer[ Index(((int)now.top), 5) ]);
		now.buffer[ Index(now.top, 5) ] = ((int)((P0 *)_this)->d);
#ifdef VAR_RANGES
		logval("buffer[top]", ((int)now.buffer[ Index(((int)now.top), 5) ]));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 26: // STATE 13 - bb.pml:31 - [top = (top+1)] (0:0:1 - 1)
		IfNotBlocked
		reached[0][13] = 1;
		(trpt+1)->bup.oval = ((int)now.top);
		now.top = (((int)now.top)+1);
#ifdef VAR_RANGES
		logval("top", ((int)now.top));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 27: // STATE 14 - bb.pml:32 - [in_cs[_pid] = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[0][14] = 1;
		(trpt+1)->bup.oval = ((int)now.in_cs[ Index(((int)((P0 *)_this)->_pid), 2) ]);
		now.in_cs[ Index(((P0 *)_this)->_pid, 2) ] = 0;
#ifdef VAR_RANGES
		logval("in_cs[_pid]", ((int)now.in_cs[ Index(((int)((P0 *)_this)->_pid), 2) ]));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 28: // STATE 15 - bb.pml:21 - [s = (s+1)] (0:0:1 - 1)
		IfNotBlocked
		reached[0][15] = 1;
		(trpt+1)->bup.oval = ((int)now.s);
		now.s = (((int)now.s)+1);
#ifdef VAR_RANGES
		logval("s", ((int)now.s));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 29: // STATE 18 - bb.pml:21 - [notEmpty = (notEmpty+1)] (0:0:1 - 1)
		IfNotBlocked
		reached[0][18] = 1;
		(trpt+1)->bup.oval = ((int)now.notEmpty);
		now.notEmpty = (((int)now.notEmpty)+1);
#ifdef VAR_RANGES
		logval("notEmpty", ((int)now.notEmpty));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 30: // STATE 21 - bb.pml:35 - [pcount = (pcount+1)] (0:0:1 - 1)
		IfNotBlocked
		reached[0][21] = 1;
		(trpt+1)->bup.oval = ((int)now.pcount);
		now.pcount = (((int)now.pcount)+1);
#ifdef VAR_RANGES
		logval("pcount", ((int)now.pcount));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 31: // STATE 27 - bb.pml:38 - [-end-] (0:0:0 - 3)
		IfNotBlocked
		reached[0][27] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */
	case  _T5:	/* np_ */
		if (!((!(trpt->o_pm&4) && !(trpt->tau&128))))
			continue;
		/* else fall through */
	case  _T2:	/* true */
		_m = 3; goto P999;
#undef rand
	}

