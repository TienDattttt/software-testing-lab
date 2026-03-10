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

		 /* CLAIM producer_no_overwrite */
	case 3: // STATE 1 - _spin_nvr.tmp:23 - [(!((!(prod_evt)||(prod_pre_flag==0))))] (6:0:0 - 1)
		
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
		reached[4][1] = 1;
		if (!( !(( !(((int)now.prod_evt))||(((int)now.prod_pre_flag)==0)))))
			continue;
		/* merge: assert(!(!((!(prod_evt)||(prod_pre_flag==0)))))(0, 2, 6) */
		reached[4][2] = 1;
		spin_assert( !( !(( !(((int)now.prod_evt))||(((int)now.prod_pre_flag)==0)))), " !( !(( !(prod_evt)||(prod_pre_flag==0))))", II, tt, t);
		/* merge: .(goto)(0, 7, 6) */
		reached[4][7] = 1;
		;
		_m = 3; goto P999; /* 2 */
	case 4: // STATE 10 - _spin_nvr.tmp:28 - [-end-] (0:0:0 - 1)
		
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
		reached[4][10] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* CLAIM non_starvation */
	case 5: // STATE 1 - _spin_nvr.tmp:12 - [(!(cons_evt))] (0:0:0 - 1)
		
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
		if (!( !(((int)now.cons_evt))))
			continue;
		_m = 3; goto P999; /* 0 */
	case 6: // STATE 8 - _spin_nvr.tmp:17 - [(!(cons_evt))] (0:0:0 - 1)
		
#if defined(VERI) && !defined(NP)
#if NCLAIMS>1
		{	static int reported8 = 0;
			if (verbose && !reported8)
			{	int nn = (int) ((Pclaim *)pptr(0))->_n;
				printf("depth %ld: Claim %s (%d), state %d (line %d)\n",
					depth, procname[spin_c_typ[nn]], nn, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported8 = 1;
				fflush(stdout);
		}	}
#else
		{	static int reported8 = 0;
			if (verbose && !reported8)
			{	printf("depth %d: Claim, state %d (line %d)\n",
					(int) depth, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported8 = 1;
				fflush(stdout);
		}	}
#endif
#endif
		reached[3][8] = 1;
		if (!( !(((int)now.cons_evt))))
			continue;
		_m = 3; goto P999; /* 0 */
	case 7: // STATE 13 - _spin_nvr.tmp:19 - [-end-] (0:0:0 - 1)
		
#if defined(VERI) && !defined(NP)
#if NCLAIMS>1
		{	static int reported13 = 0;
			if (verbose && !reported13)
			{	int nn = (int) ((Pclaim *)pptr(0))->_n;
				printf("depth %ld: Claim %s (%d), state %d (line %d)\n",
					depth, procname[spin_c_typ[nn]], nn, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported13 = 1;
				fflush(stdout);
		}	}
#else
		{	static int reported13 = 0;
			if (verbose && !reported13)
			{	printf("depth %d: Claim, state %d (line %d)\n",
					(int) depth, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported13 = 1;
				fflush(stdout);
		}	}
#endif
#endif
		reached[3][13] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* CLAIM mutual_exclusion */
	case 8: // STATE 1 - _spin_nvr.tmp:3 - [(!(!((in_cs[0]&&in_cs[1]))))] (6:0:0 - 1)
		
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
	case 9: // STATE 10 - _spin_nvr.tmp:8 - [-end-] (0:0:0 - 1)
		
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
	case 10: // STATE 1 - pc.pml:39 - [((flag==1))] (0:0:0 - 1)
		IfNotBlocked
		reached[1][1] = 1;
		if (!((((int)now.flag)==1)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 11: // STATE 2 - pc.pml:18 - [((lock==0))] (6:0:1 - 1)
		IfNotBlocked
		reached[1][2] = 1;
		if (!((((int)now.lock)==0)))
			continue;
		/* merge: lock = 1(0, 3, 6) */
		reached[1][3] = 1;
		(trpt+1)->bup.oval = ((int)now.lock);
		now.lock = 1;
#ifdef VAR_RANGES
		logval("lock", ((int)now.lock));
#endif
		;
		_m = 3; goto P999; /* 1 */
	case 12: // STATE 6 - pc.pml:41 - [in_cs[_pid] = 1] (0:0:1 - 1)
		IfNotBlocked
		reached[1][6] = 1;
		(trpt+1)->bup.oval = ((int)now.in_cs[ Index(((int)((P1 *)_this)->_pid), 2) ]);
		now.in_cs[ Index(((P1 *)_this)->_pid, 2) ] = 1;
#ifdef VAR_RANGES
		logval("in_cs[_pid]", ((int)now.in_cs[ Index(((int)((P1 *)_this)->_pid), 2) ]));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 13: // STATE 7 - pc.pml:42 - [cons_pre_flag = flag] (0:0:1 - 1)
		IfNotBlocked
		reached[1][7] = 1;
		(trpt+1)->bup.oval = ((int)cons_pre_flag);
		cons_pre_flag = ((int)now.flag);
#ifdef VAR_RANGES
		logval("cons_pre_flag", ((int)cons_pre_flag));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 14: // STATE 8 - pc.pml:44 - [flag = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[1][8] = 1;
		(trpt+1)->bup.oval = ((int)now.flag);
		now.flag = 0;
#ifdef VAR_RANGES
		logval("flag", ((int)now.flag));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 15: // STATE 9 - pc.pml:45 - [in_cs[_pid] = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[1][9] = 1;
		(trpt+1)->bup.oval = ((int)now.in_cs[ Index(((int)((P1 *)_this)->_pid), 2) ]);
		now.in_cs[ Index(((P1 *)_this)->_pid, 2) ] = 0;
#ifdef VAR_RANGES
		logval("in_cs[_pid]", ((int)now.in_cs[ Index(((int)((P1 *)_this)->_pid), 2) ]));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 16: // STATE 10 - pc.pml:46 - [cons_evt = 1] (0:0:1 - 1)
		IfNotBlocked
		reached[1][10] = 1;
		(trpt+1)->bup.oval = ((int)now.cons_evt);
		now.cons_evt = 1;
#ifdef VAR_RANGES
		logval("cons_evt", ((int)now.cons_evt));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 17: // STATE 11 - pc.pml:47 - [cons_evt = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[1][11] = 1;
		(trpt+1)->bup.oval = ((int)now.cons_evt);
		now.cons_evt = 0;
#ifdef VAR_RANGES
		logval("cons_evt", ((int)now.cons_evt));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 18: // STATE 12 - pc.pml:21 - [lock = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[1][12] = 1;
		(trpt+1)->bup.oval = ((int)now.lock);
		now.lock = 0;
#ifdef VAR_RANGES
		logval("lock", ((int)now.lock));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 19: // STATE 18 - pc.pml:50 - [-end-] (0:0:0 - 1)
		IfNotBlocked
		reached[1][18] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* PROC Producer */
	case 20: // STATE 1 - pc.pml:25 - [((flag==0))] (0:0:0 - 1)
		IfNotBlocked
		reached[0][1] = 1;
		if (!((((int)now.flag)==0)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 21: // STATE 2 - pc.pml:18 - [((lock==0))] (6:0:1 - 1)
		IfNotBlocked
		reached[0][2] = 1;
		if (!((((int)now.lock)==0)))
			continue;
		/* merge: lock = 1(0, 3, 6) */
		reached[0][3] = 1;
		(trpt+1)->bup.oval = ((int)now.lock);
		now.lock = 1;
#ifdef VAR_RANGES
		logval("lock", ((int)now.lock));
#endif
		;
		_m = 3; goto P999; /* 1 */
	case 22: // STATE 6 - pc.pml:27 - [in_cs[_pid] = 1] (0:0:1 - 1)
		IfNotBlocked
		reached[0][6] = 1;
		(trpt+1)->bup.oval = ((int)now.in_cs[ Index(((int)((P0 *)_this)->_pid), 2) ]);
		now.in_cs[ Index(((P0 *)_this)->_pid, 2) ] = 1;
#ifdef VAR_RANGES
		logval("in_cs[_pid]", ((int)now.in_cs[ Index(((int)((P0 *)_this)->_pid), 2) ]));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 23: // STATE 7 - pc.pml:28 - [prod_pre_flag = flag] (0:0:1 - 1)
		IfNotBlocked
		reached[0][7] = 1;
		(trpt+1)->bup.oval = ((int)now.prod_pre_flag);
		now.prod_pre_flag = ((int)now.flag);
#ifdef VAR_RANGES
		logval("prod_pre_flag", ((int)now.prod_pre_flag));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 24: // STATE 8 - pc.pml:30 - [flag = 1] (0:0:1 - 1)
		IfNotBlocked
		reached[0][8] = 1;
		(trpt+1)->bup.oval = ((int)now.flag);
		now.flag = 1;
#ifdef VAR_RANGES
		logval("flag", ((int)now.flag));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 25: // STATE 9 - pc.pml:31 - [in_cs[_pid] = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[0][9] = 1;
		(trpt+1)->bup.oval = ((int)now.in_cs[ Index(((int)((P0 *)_this)->_pid), 2) ]);
		now.in_cs[ Index(((P0 *)_this)->_pid, 2) ] = 0;
#ifdef VAR_RANGES
		logval("in_cs[_pid]", ((int)now.in_cs[ Index(((int)((P0 *)_this)->_pid), 2) ]));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 26: // STATE 10 - pc.pml:32 - [prod_evt = 1] (0:0:1 - 1)
		IfNotBlocked
		reached[0][10] = 1;
		(trpt+1)->bup.oval = ((int)now.prod_evt);
		now.prod_evt = 1;
#ifdef VAR_RANGES
		logval("prod_evt", ((int)now.prod_evt));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 27: // STATE 11 - pc.pml:33 - [prod_evt = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[0][11] = 1;
		(trpt+1)->bup.oval = ((int)now.prod_evt);
		now.prod_evt = 0;
#ifdef VAR_RANGES
		logval("prod_evt", ((int)now.prod_evt));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 28: // STATE 12 - pc.pml:21 - [lock = 0] (0:0:1 - 1)
		IfNotBlocked
		reached[0][12] = 1;
		(trpt+1)->bup.oval = ((int)now.lock);
		now.lock = 0;
#ifdef VAR_RANGES
		logval("lock", ((int)now.lock));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 29: // STATE 18 - pc.pml:36 - [-end-] (0:0:0 - 1)
		IfNotBlocked
		reached[0][18] = 1;
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

