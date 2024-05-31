package PipelineGame.model.fillers;

import PipelineGame.model.pipeline.devices.HeatingDevice;
import PipelineGame.model.pipeline.devices.RefrigerationDevice;
import PipelineGame.model.pipeline.devices.SaltFilteringDevice;
import PipelineGame.model.pipeline.devices.WaterDevice;
import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Pipe;
import PipelineGame.model.pipeline.segments.Tap;
import PipelineGame.model.pipeline.segments.pipes.Adapter;
import PipelineGame.model.pipeline.segments.pipes.Corner;
import PipelineGame.model.pipeline.segments.pipes.Cross;
import PipelineGame.model.pipeline.segments.pipes.Tee;

import java.util.Random;

public class SegmentFactory {
    public Tap createTap() {
        return new Tap();
    }

    public Hatch createHatch() {
        return new Hatch();
    }

    public Pipe createPipe(PipeType type) {
        Pipe pipe = null;
        if (type == PipeType.Corner) {
            pipe = new Corner();
        } else if (type == PipeType.Tee) {
            pipe = new Tee();
        } else if (type == PipeType.Cross) {
            pipe = new Cross();
        } else if (type == PipeType.Adapter) {
            pipe = new Adapter();
        }

        if (pipe == null) {
            throw new RuntimeException("Unknown PipeType");
        }

        int countDevices = new Random().nextInt(3) - 2;
        for (int i = 0; i <= countDevices; i++) {
            pipe.addDevice(this.getRandomWaterDevice());
        }
        return pipe;
    }

    public WaterDevice getRandomWaterDevice() {
        Random random = new Random();
        int chance = random.nextInt(100);

        if (chance > 40 && chance < 80) {
            return new RefrigerationDevice();
        } else if (chance > 80 && chance < 99) {
            return new SaltFilteringDevice();
        }
        return new HeatingDevice();
    }
}
