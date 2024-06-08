package PipelineGame.ui;

import PipelineGame.model.pipeline.devices.HeatingDevice;
import PipelineGame.model.pipeline.devices.RefrigerationDevice;
import PipelineGame.model.pipeline.devices.SaltFilteringDevice;
import PipelineGame.model.pipeline.devices.WaterDevice;
import PipelineGame.ui.segments.SegmentView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static PipelineGame.ui.utils.ImageHelper.getScaledIcon;
import static PipelineGame.ui.utils.ImageHelper.overlayImages;
import static PipelineGame.ui.utils.ImageLoader.loadAsImageIcon;

public class DevicesView {
    private static final HashMap<Class<? extends WaterDevice>, ImageIcon> devicesIcons = new HashMap<>() {{
        put(HeatingDevice.class, loadAsImageIcon("heating-device.png"));
        put(RefrigerationDevice.class, loadAsImageIcon("refrigeration-device.png"));
        put(SaltFilteringDevice.class, loadAsImageIcon("filtering-device.png"));
    }};

    public static ImageIcon drawDevicesIcons(ImageIcon segmentIcon, ArrayList<WaterDevice> devices) {
        int offset, x, y; offset = x = y = 1;

        int SIDE_SIZE = (int) (SegmentView.SIDE_SIZE / 3.6);
        Dimension deviceIconSize = new Dimension(SIDE_SIZE, SIDE_SIZE);
        ImageIcon resultIcon = new ImageIcon(segmentIcon.getImage());

        for (WaterDevice device : devices) {
            ImageIcon icon = devicesIcons.get(device.getClass());

            icon = getScaledIcon(icon, deviceIconSize);
            resultIcon = overlayImages(resultIcon, icon, x, y);
            x += deviceIconSize.width + offset;

            if (x > SegmentView.SIDE_SIZE - deviceIconSize.width) {
                x = offset;
                y += deviceIconSize.height + offset;
            }
        }
        return resultIcon;
    }
}
