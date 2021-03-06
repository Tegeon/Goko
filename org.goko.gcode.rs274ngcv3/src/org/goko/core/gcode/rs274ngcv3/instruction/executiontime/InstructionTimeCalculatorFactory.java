package org.goko.core.gcode.rs274ngcv3.instruction.executiontime;

import java.util.ArrayList;
import java.util.List;

import org.goko.core.common.exception.GkException;
import org.goko.core.common.measure.quantity.Time;
import org.goko.core.gcode.rs274ngcv3.context.GCodeContext;
import org.goko.core.gcode.rs274ngcv3.instruction.AbstractInstruction;

/**
 * Instruction factory
 *
 * @author Psyko
 */
public class InstructionTimeCalculatorFactory {
	/** The list of known calculators*/
	public List<AbstractInstructionTimeCalculator<? extends AbstractInstruction>> calculators;
	
	/** Constructor */
	public InstructionTimeCalculatorFactory() {
		calculators = new ArrayList<AbstractInstructionTimeCalculator<? extends AbstractInstruction>>();
		calculators.add(new StraightFeedTimeCalculator());
		calculators.add(new StraightTraverseTimeCalculator());
		calculators.add(new ArcFeedTimeCalculator());
		calculators.add(new StraightProbeTimeCalculator());
		calculators.add(new DwellTimeCalculator());
	}
	
	/**
	 * Evaluate the time required to execute the given instruction  
	 * @param context the context 
	 * @param instruction the instruction to evaluate 
	 * @return a Time quantity
	 * @throws GkException GkException
	 */
	public Time getExecutionTime(GCodeContext context, AbstractInstruction instruction) throws GkException{
		for (AbstractInstructionTimeCalculator<? extends AbstractInstruction> calculator : calculators) {
			if(instruction.getType().equals(calculator.getInstructionType())){
				return calculator.getExecutionTime(context, instruction);
			}			
		}
		return Time.ZERO;
	}
}
