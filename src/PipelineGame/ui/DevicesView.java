package PipelineGame.ui;

import PipelineGame.AppSettings;
import PipelineGame.model.pipeline.devices.HeatingDevice;
import PipelineGame.model.pipeline.devices.RefrigerationDevice;
import PipelineGame.model.pipeline.devices.SaltFilteringDevice;
import PipelineGame.model.pipeline.devices.WaterDevice;
import PipelineGame.ui.segments.SegmentView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static PipelineGame.AppSettings.DEFAULT_SEGMENT_SIDE_SIZE;
import static PipelineGame.ui.ImageHelper.getScaledIcon;
import static PipelineGame.ui.ImageHelper.overlayImages;

public class DevicesView {
    private static final HashMap<Class<? extends WaterDevice>, ImageIcon> devicesIcons = new HashMap<>() {{
        put(HeatingDevice.class, new ImageIcon(AppSettings.pathToImages + "heating-device.png"));
        put(RefrigerationDevice.class, new ImageIcon(AppSettings.pathToImages + "refrigeration-device.png"));
        put(SaltFilteringDevice.class, new ImageIcon(AppSettings.pathToImages + "filtering-device.png"));
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
