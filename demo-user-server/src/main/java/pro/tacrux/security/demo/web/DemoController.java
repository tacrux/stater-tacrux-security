package pro.tacrux.security.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.tacrux.security.model.R;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> wangtao@360humi.com
 * <b>Date:</b> 2022/9/15 15:38
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/15 15:38    wangtao@360humi.com     new file.
 * </pre>
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/delete")
    public R<String> delete() {
        return R.ok("delete");
    }

    @GetMapping("/save")
    public R<String> save() {
        return R.ok("save");
    }

    @GetMapping("/query")
    public R<String> query() {
        return R.ok("query");
    }
}
