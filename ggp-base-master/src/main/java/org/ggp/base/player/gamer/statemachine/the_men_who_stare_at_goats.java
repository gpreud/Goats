package org.ggp.base.player.gamer.statemachine;


import java.util.ArrayList;
import java.util.List;

import org.ggp.base.apps.player.Player;
import org.ggp.base.player.gamer.exception.GamePreviewException;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.Role;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.cache.CachedStateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;
import org.ggp.base.util.statemachine.implementation.prover.ProverStateMachine;


public class the_men_who_stare_at_goats extends StateMachineGamer {
	protected Player p;

	@Override
	public StateMachine getInitialStateMachine() {
		return new CachedStateMachine(new ProverStateMachine());
	}

	@Override
	public void stateMachineMetaGame(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException,
			GoalDefinitionException {
		// TODO Auto-generated method stub

	}

	private int maxscore(Role role, MachineState state, StateMachine machine)
			throws GoalDefinitionException, MoveDefinitionException, TransitionDefinitionException {
		if(machine.isTerminal(state)) {
			return machine.getGoal(state, role);
		}

		List<Move> moves = machine.getLegalMoves(state, role);
		int score = 0;
		for(int i = 0; i < moves.size(); i++) {
			List<Move> list = new ArrayList<Move>();
			list.add(moves.get(i));
			int result = maxscore(role, machine.getNextState(state, list), machine);

			if(result == 100) {
				return 100;
			} else if(result > score) {
				score = result;
			}
		}
		return score;
	}

	protected Move bestmove(Role role, StateMachine machine)
			throws MoveDefinitionException, TransitionDefinitionException, GoalDefinitionException {
		MachineState state = getCurrentState();
		List<Move> moves = machine.getLegalMoves(state, role);
		Move action = moves.get(0);
		int score = 0;
		for(int i = 0; i < moves.size(); i++) {
			List<Move> list = new ArrayList<Move>();
			list.add(moves.get(i));
			int result = maxscore(role, machine.getNextState(state, list), machine);

			if(result == 100) {
				return moves.get(i);
			} else if(result > score) {
				score = result;
				action = moves.get(i);
			}
		}


		return action;
	}

	@Override
	public Move stateMachineSelectMove(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException,
			GoalDefinitionException {
		StateMachine machine = getStateMachine();
		Role role = getRole();

		return bestmove(role, machine);

	}

	@Override
	public void stateMachineStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateMachineAbort() {
		// TODO Auto-generated method stub

	}

	@Override
	public void preview(Game g, long timeout) throws GamePreviewException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "the_men_who_stare_at_goats Player";
	}

}
