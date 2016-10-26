/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2016 Board of Regents of the University of
 * Wisconsin-Madison, University of Konstanz and Brian Northan.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imagej.ops.map.neighborhood;

import net.imagej.ops.Ops;
import net.imagej.ops.special.computer.Computers;
import net.imagej.ops.special.computer.UnaryComputerOp;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.neighborhood.Neighborhood;

import org.scijava.Priority;
import org.scijava.plugin.Plugin;

/**
 * Evaluates a {@link UnaryComputerOp} for each {@link Neighborhood} on the
 * input {@link RandomAccessibleInterval}.
 * 
 * @author Christian Dietz (University of Konstanz)
 * 
 * @param <I> input type
 * @param <O> output type
 */
@Plugin(type = Ops.Map.class, priority = Priority.LOW_PRIORITY)
public class DefaultMapNeighborhood<I, O> extends
	AbstractMapNeighborhood<RandomAccessibleInterval<I>, IterableInterval<O>, UnaryComputerOp<Iterable<I>, O>>
{

	private UnaryComputerOp<IterableInterval<Neighborhood<I>>, IterableInterval<O>> map;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize() {
		map = (UnaryComputerOp) Computers.unary(ops(), Ops.Map.class,
			IterableInterval.class, in() == null ? IterableInterval.class : getShape()
				.neighborhoods(in()), getOp());
	}

	@Override
	public void compute1(final RandomAccessibleInterval<I> in, 
		final IterableInterval<O> out)
	{
		map.compute1(getShape().neighborhoodsSafe(in), out);
	}

}
