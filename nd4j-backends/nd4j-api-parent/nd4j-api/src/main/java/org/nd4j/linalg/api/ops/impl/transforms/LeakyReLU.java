/*-
 *
 *  * Copyright 2015 Skymind,Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package org.nd4j.linalg.api.ops.impl.transforms;

import org.nd4j.autodiff.functions.DifferentialFunction;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.BaseTransformOp;

import java.util.Collections;
import java.util.List;

/**Leaky Rectified linear unit. Default alpha=0.01, cutoff=0<br>
 * Out(x) = alpha*x if x<0<br>
 * Out(x) = x if x >= 0<br>
 * Leaky ReLU may avoid zero gradient "dying ReLU" problem by having non-zero
 * gradient below 0.<br>
 * See for example http://arxiv.org/abs/1505.00853 for a comparison of
 * ReLU variants.
 * @author Alex Black
 */
public class LeakyReLU extends BaseTransformOp {
    public static final double DEFAULT_ALPHA = 0.01;
    private double alpha = DEFAULT_ALPHA;

    public LeakyReLU(SameDiff sameDiff, DifferentialFunction i_v, boolean inPlace, double alpha) {
        super(sameDiff, i_v, inPlace);
        this.alpha = alpha;
    }

    public LeakyReLU(SameDiff sameDiff, DifferentialFunction i_v, int[] shape, boolean inPlace, Object[] extraArgs, double alpha) {
        super(sameDiff, i_v, shape, inPlace, extraArgs);
        this.alpha = alpha;
    }

    public LeakyReLU(SameDiff sameDiff, DifferentialFunction i_v, Object[] extraArgs, double alpha) {
        super(sameDiff, i_v, extraArgs);
        this.alpha = alpha;
    }

    public LeakyReLU() {
        super();
    }

    public LeakyReLU(INDArray x, double alpha) {
        super(x);
        this.alpha = alpha;
        init(x, y, z, n); //Need to re-init to properly set alpha in extra args array
    }

    public LeakyReLU(INDArray x, INDArray z, double alpha) {
        super(x, z);
        this.alpha = alpha;
        init(x, y, z, n);
    }

    public LeakyReLU(INDArray x, INDArray z, long n, double alpha) {
        super(x, z, n);
        this.alpha = alpha;
        init(x, y, z, n);
    }

    public LeakyReLU(INDArray x, INDArray y, INDArray z, long n, double alpha) {
        super(x, y, z, n);
        this.alpha = alpha;
        init(x, y, z, n);
    }

    public LeakyReLU(INDArray x, INDArray z) {
        super(x, z);
    }

    public LeakyReLU(INDArray x, INDArray z, long n) {
        super(x, z, n);
    }

    public LeakyReLU(INDArray x, INDArray y, INDArray z, long n) {
        super(x, y, z, n);
    }

    public LeakyReLU(INDArray x) {
        super(x);
        this.extraArgs = new Object[] {alpha};
    }

    @Override
    public int opNum() {
        return 31;
    }

    @Override
    public String opName() {
        return "leakyrelu";
    }


    @Override
    public void init(INDArray x, INDArray y, INDArray z, long n) {
        super.init(x, y, z, n);
        this.extraArgs = new Object[] {alpha};
    }

    @Override
    public String onnxName() {
        return "LeakyRelu";
    }

    @Override
    public String tensorflowName() {
        return "LeakyRelu";
    }



    @Override
    public List<DifferentialFunction> doDiff(List<DifferentialFunction> i_v) {
        DifferentialFunction ret = f().leakyReluDerivative(arg(),i_v.get(0) , alpha);

        return Collections.singletonList(ret);
    }
}
